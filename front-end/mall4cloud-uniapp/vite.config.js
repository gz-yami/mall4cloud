import { defineConfig } from 'vite'
import uni from '@dcloudio/vite-plugin-uni'
import path from 'path'
import AutoImport from 'unplugin-auto-import/vite'
import h5ProdEffectPlugin from 'uni-vite-plugin-h5-prod-effect'
import { compression } from 'vite-plugin-compression2'

// APP nvue 二阶段会默认导入页面入口，开启 Rollup tree-shaking 会把未被引用的 default export 裁掉。
const isAppBuild = ['app', 'app-plus', 'app-android', 'app-ios', 'app-harmony'].includes(process.env.UNI_PLATFORM)
const isH5Build = process.env.UNI_PLATFORM === 'h5'
const h5BuildTargets = ['chrome64', 'firefox67', 'safari11.1', 'edge79']

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    uni(),
    AutoImport({
      imports: [
        'vue',
        'uni-app'
      ],
      dirs: [
        'src/utils',
        'src/wxs/**'
      ],
      dts: 'src/auto-imports.d.ts',
      eslintrc: {
        enabled: true
      }
    })
  ].concat(isH5Build ? [
    // 对 H5 production 环境打包时的特殊处理，否则 uni-crazy-router 在这个环境会异常。
    h5ProdEffectPlugin(),
    // H5 产物生成 gzip 预压缩文件。
    compression({
      threshold: 1024,
      algorithms: ['gzip']
    })
  ] : []),
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')
    }
  },
  server: {
    host: true,
    port: 80,
    open: true
  },
  build: {
    // App 端内置 JS 引擎兼容性弱于现代浏览器，显式降级避免产物残留逻辑赋值等新语法导致白屏。
    target: isAppBuild ? 'es2018' : isH5Build ? h5BuildTargets : undefined,
    // H5 CSS 单独使用更保守目标，避免输出对 Android/微信 WebView 不友好的新 CSS 语法。
    cssTarget: isH5Build ? 'chrome53' : undefined,
    minify: 'terser',
    rollupOptions: {
      treeshake: isAppBuild ? false : undefined
    }
  },
  esbuild: {
    supported: {
      // H5 目标浏览器均支持解构；避免 esbuild 尝试降级解构时报 unsupported transform。
      destructuring: true
    }
  }
})
