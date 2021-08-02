## 防范`xss`攻击

为了防止用户传入一些假数据、假脚本对系统做出攻击，比较出名的就是`xss`攻击。故此，本商城在后台代码中采用过滤器来解决`xss`攻击。

在`mall4cloud`这个项目里面，使用了一个过滤器 `XssFilter`

```java
public class XssFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(XssFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// replaceAll("[\r\n]" =》 Potential CRLF Injection for logs
		logger.info("AuthFilter RequestURI :{}", req.getRequestURI().replaceAll("[\r\n]",""));
		// xss 过滤
		chain.doFilter(new XssWrapper(req), resp);
	}
}
```

主要是通过 `new XssWrapper(req)` 这个对象进行一系列的过滤，而 `XssWrapper` 是通过`Jsoup`进行用户输入的一系列过滤。毕竟专业的事情要交给专业的人来搞定。就此，我们通过简单的设置就完成了对**xss攻击**的防御。

```java
public class XssWrapper extends HttpServletRequestWrapper {

	/**
	 * Constructs a request object wrapping the given request.
	 * @param request The request to wrap
	 * @throws IllegalArgumentException if the request is null
	 */
	public XssWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 对数组参数进行特殊字符过滤
	 */
	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXss(values[i]);
		}
		return encodedValues;
	}

	/**
	 * 对参数中特殊字符进行过滤
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (StrUtil.isBlank(value)) {
			return value;
		}
		return cleanXss(value);
	}

	/**
	 * 获取attribute,特殊字符过滤
	 */
	@Override
	public Object getAttribute(String name) {
		Object value = super.getAttribute(name);
		if (value instanceof String && StrUtil.isNotBlank((String) value)) {
			return cleanXss((String) value);
		}
		return value;
	}

	/**
	 * 对请求头部进行特殊字符过滤
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (StrUtil.isBlank(value)) {
			return value;
		}
		return cleanXss(value);
	}

	private String cleanXss(String value) {
		return XssUtil.clean(value);
	}
}
```

这里面最主要的方法就是`XssUtil.clean(value)` -> `Jsoup.clean(content, "", WHITE_LIST, OUTPUT_SETTINGS)`  其中最重要的是有个白名单列表 `WHITE_LIST` ，该白名单列表里面是部分携带html的部分标签进入，从而防范xss攻击。

```java
new Whitelist().addTags(
                        "a", "b", "blockquote", "br", "caption", "cite", "code", "col",
                        "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
                        "i", "img", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong",
                        "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
                        "ul")
                .addAttributes("a", "href", "title")
                .addAttributes("blockquote", "cite")
                .addAttributes("col", "span", "width")
                .addAttributes("colgroup", "span", "width")
                .addAttributes("img", "align", "alt", "height", "src", "title", "width")
                .addAttributes("ol", "start", "type")
                .addAttributes("q", "cite")
                .addAttributes("table", "summary", "width")
                .addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width")
                .addAttributes(
                        "th", "abbr", "axis", "colspan", "rowspan", "scope",
                        "width")
                .addAttributes("ul", "type")
                .addProtocols("a", "href", "ftp", "http", "https", "mailto")
                .addProtocols("blockquote", "cite", "http", "https")
                .addProtocols("cite", "cite", "http", "https")
                .addProtocols("img", "src", "http", "https")
                .addProtocols("q", "cite", "http", "https")
```

