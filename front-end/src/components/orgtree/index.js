import orgtree from './orgtree.vue'

export function install (Vue) {
  if (install.installed) {
    return
  }

  install.installed = true

  Vue.component(orgtree.name, orgtree)
}

orgtree.install = install

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(orgtree)
}

export default orgtree
