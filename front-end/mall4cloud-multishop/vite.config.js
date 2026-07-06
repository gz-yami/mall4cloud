import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
import { compression } from 'vite-plugin-compression2'

/**
 * 解析第三方依赖的分包名称
 * @param {String} moduleId 模块路径
 * @returns {String|null} 分包名称
 */
function resolveVendorChunkName(moduleId) {
  // 统一路径分隔符，避免 Windows 和 Linux 构建环境下路径格式不一致。
  const normalizedModuleId = moduleId.replace(/\\/g, '/')

  // 仅处理第三方依赖，业务代码交给 Vite/Rolldown 默认拆分策略。
  if (!normalizedModuleId.includes('node_modules')) {
    return null
  }

  // 兼容 pnpm 的 .pnpm 物理目录结构，保持依赖分包名称稳定。
  if (normalizedModuleId.includes('.pnpm/')) {
    return normalizedModuleId.split('.pnpm/')[1].split('/')[0].toString()
  }

  // 兼容普通 node_modules 目录结构，避免 CI 安装方式变化导致分包名称异常。
  if (normalizedModuleId.includes('node_modules/')) {
    return normalizedModuleId.split('node_modules/')[1].split('/')[0].toString()
  }

  return 'vendor'
}

// https://vitejs.dev/config/
export default defineConfig(({ command }) => {
  return {
    define: {
      // 将 Node 生态依赖中的 global 映射到浏览器标准。
      global: 'self'
    },
    plugins: [
      vue(),
      createSvgIconsPlugin({
        iconDirs: [path.resolve(process.cwd(), 'src/icons/svg')],
        symbolId: 'icon-[dir]-[name]'
      }),
      // 自动引入内容
      AutoImport({
        imports: [
          'vue',
          'vue-router',
          {
            'element-plus': [
              ['ElMessage', 'ElMessage'],
              ['ElMessageBox', 'ElMessageBox']
            ]
          }
        ],
        dirs: ['src/hooks/**', 'src/stores/**', 'src/utils/**'],
        resolvers: command === 'build' ? [ElementPlusResolver()] : [],
        dts: 'src/auto-import/imports.d.ts',
        // 继续输出自动导入的全局变量清单，供 oxlint 兼容消费
        eslintrc: {
          enabled: true,
          filepath: './.oxlintrc-auto-import.json',
          globalsPropValue: true
        }
      }),
      // 自动引入组件
      Components({
        dirs: ['src/components'],
        resolvers: command === 'build' ? [ElementPlusResolver()] : [],
        dts: 'src/auto-import/components.d.ts'
      }),
      // 对大于 1k 的文件进行压缩
      compression({
        threshold: 1024,
        algorithms: ['gzip']
      })
    ],
    server: {
      host: true,
      port: 9527,
      open: true
    },
    css: {
      lightningcss: {
        // 兼容历史样式中的 IE hack，交给 Lightning CSS 自动恢复后继续压缩。
        errorRecovery: true
      }
    },
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src'),
        'vue-i18n': 'vue-i18n/dist/vue-i18n.cjs.js'
      }
    },
    build: {
      target: ['chrome64', 'firefox67', 'safari11.1', 'edge79'],
      base: './',
      rolldownOptions: {
        // 静态资源分类打包
        output: {
          chunkFileNames: 'static/js/[name]-[hash].js',
          entryFileNames: 'static/js/[name]-[hash].js',
          assetFileNames: 'static/[ext]/[name]-[hash].[ext]',
          // 使用 Rolldown 的 codeSplitting 配置替代 manualChunks，减少 CI 和本地打包差异。
          codeSplitting: {
            groups: [
              {
                // 动态计算第三方依赖分包名称，延续原有按包拆分缓存策略。
                name(moduleId) {
                  return resolveVendorChunkName(moduleId)
                },
                // 仅匹配 node_modules 依赖，避免业务模块被归入第三方依赖分包。
                test: /node_modules[\\/]/
              }
            ]
          }
        }
      },
      sourcemap: false,
      // 主构建切换为 oxc 压缩以降低常规产物压缩耗时
      minify: 'oxc',
      reportCompressedSize: false
    }
  }
})
