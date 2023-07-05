package com.crypted.explorer.common.i18n.context

import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.io.IOException
import java.util.*

class LocalReloadableResourceBundleMessageSource : ReloadableResourceBundleMessageSource() {
    private val resolver = PathMatchingResourcePatternResolver()
    override fun refreshProperties(filename: String, propHolder: PropertiesHolder?): PropertiesHolder {
        return if (filename.startsWith(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX)) {
            refreshClassPathProperties(filename, propHolder)
        } else {
            super.refreshProperties(filename, propHolder)
        }
    }

    private fun refreshClassPathProperties(filename: String, propHolder: PropertiesHolder?): PropertiesHolder {
        val properties = Properties()
        var lastModified: Long = -1
        try {
            val resources = resolver.getResources(filename + PROPERTIES_SUFFIX)
            for (resource in resources) {
                val sourcePath = resource.uri.toString().replace(PROPERTIES_SUFFIX, "")
                val holder = super.refreshProperties(sourcePath, propHolder)
                properties.putAll(holder.properties!!)
                if (lastModified < resource.lastModified()) lastModified = resource.lastModified()
            }
        } catch (ignored: IOException) {
        }
        return PropertiesHolder(properties, lastModified)
    }

    companion object {
        private const val PROPERTIES_SUFFIX = ".properties"
    }
}