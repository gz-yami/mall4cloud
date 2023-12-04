
module.exports = {
  env: {
    browser: true,
    es2021: true,
    node: true
  },
  globals: {
    uni: 'readonly',
    getApp: 'readonly',
    wx: 'readonly',
    getCurrentPages: 'readonly',
    plus: 'readonly'
  },
  extends: [
    'standard',
    './.eslintrc-auto-import.json',
    'plugin:vue/vue3-recommended',
    'plugin:vue-scoped-css/vue3-recommended'
  ],
  overrides: [
  ],
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module'
  },
  plugins: [
    'vue'
  ],
  rules: {
    // Possible Errors
    // 要求使用 let 或 const 而不是 var
    'no-var': 'error',
    // 强制 "for" 循环中更新子句的计数器朝着正确的方向移动
    'for-direction': 'error',
    // 强制 getter 函数中出现 return 语句
    'getter-return': 'error',
    // 禁止在嵌套的块中出现变量声明或 function 声明
    'no-inner-declarations': 'error',
    // 禁止由于 await 或 yield的使用而可能导致出现竞态条件的赋值
    'require-atomic-updates': 'error',
    // console 警告
    'no-console': 'warn',
    // 禁止出现未使用过的变量
    'no-unused-vars': [
      'warn',
      {
        args: 'all',
        caughtErrors: 'none',
        ignoreRestSiblings: true,
        vars: 'all'
      }
    ],
    // 关闭名称校验
    'vue/multi-word-component-names': 'off',
    // 非生产环境启用 debugger
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',

    // Best Practices
    eqeqeq: 'off',

    // Stylistic Issues
    // 强制可嵌套的块的最大深度
    'max-depth': ['error', 5],
    // 强制函数最大代码行数
    'max-lines-per-function': [
      'error',
      {
        max: 150,
        skipBlankLines: true
      }
    ],
    // 强制回调函数最大嵌套深度
    'max-nested-callbacks': ['error', { max: 10 }],
    // 强制函数定义中最多允许的参数数量
    'max-params': ['error', { max: 5 }],
    // 强制每一行中所允许的最大语句数量
    'max-statements-per-line': ['error', { max: 1 }],
    // 三目运算符换行
    'multiline-ternary': ['error', 'never'],
    // 传值给组件时的使用 kebab-case
    'vue/v-on-event-hyphenation': ['warn', 'always', {
      autofix: true,
      ignore: []
    }]
  }
}
