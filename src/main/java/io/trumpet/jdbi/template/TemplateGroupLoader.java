package io.trumpet.jdbi.template;

import com.nesscomputing.logging.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.antlr.stringtemplate.StringTemplateErrorListener;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;

public class TemplateGroupLoader
{
	private static final Log LOG = Log.findLog();

    public static StringTemplateGroup load(final String name)
    {
        final URL resourceUrl = TemplateGroupLoader.class.getClassLoader().getResource(name);
        if (resourceUrl == null) {
            throw new TemplateLoaderException("Error loading StringTemplate: Resource %s does not exist!", name);
        }
        return load(name, resourceUrl);
    }

    public static List<StringTemplateGroup> loadAll(final String name)
    {
        try {
            Enumeration<URL> resourceUrls = TemplateGroupLoader.class.getClassLoader().getResources(name);
            List<StringTemplateGroup> groups = new ArrayList<StringTemplateGroup>();

            while (resourceUrls.hasMoreElements()) {
                groups.add(load(name, resourceUrls.nextElement()));
            }
            return groups;
        }
        catch (IOException ex) {
            throw new TemplateLoaderException(ex, "Error loading StringTemplate: %s", name);
        }
    }

    public static StringTemplateGroup load(final String name, final URL resourceUrl)
    {
        if (resourceUrl == null) {
            throw new TemplateLoaderException("Error loading StringTemplate: Resource %s does not exist!", name);
        }
        Reader reader;

        try {
            reader = new InputStreamReader(resourceUrl.openStream(), Charset.forName("UTF-8"));
        }
        catch (IOException ex) {
            throw new TemplateLoaderException(ex, "Error loading StringTemplate: %s", name);
        }

        final AtomicBoolean error = new AtomicBoolean(false);

        final StringTemplateGroup result = new StringTemplateGroup(reader, AngleBracketTemplateLexer.class, new StringTemplateErrorListener() {
            @Override
            public void error(final String msg, final Throwable e)
            {
                LOG.error(e, msg);
                error.set(true);
            }

            @Override
            public void warning(final String msg)
            {
                LOG.warn(msg);
            }
        });

        if (error.get()) {
            throw new TemplateLoaderException("Error loading StringTemplate: %s", name);
        }

        return result;
    }

    public static StringTemplateGroup load(final String namespace, final String name)
    {
        return load(namespace.replace(".", "/") + name);
    }

    public static List<StringTemplateGroup> loadAll(final String namespace, final String name)
    {
        return loadAll(namespace.replace(".", "/") + name);
    }

    public static StringTemplateGroup load(final Class<?> classObj, final String name)
    {
        return load(classObj.getPackage().getName(), name);
    }

    public static List<StringTemplateGroup> loadAll(final Class<?> classObj, final String name)
    {
        return loadAll(classObj.getPackage().getName(), name);
    }
}
