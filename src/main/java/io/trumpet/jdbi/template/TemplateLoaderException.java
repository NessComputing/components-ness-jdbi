package io.trumpet.jdbi.template;

/**
 * An Exception for loading templates. Note that this has message and throwable swapped to allow varargs error messages.
 */
public class TemplateLoaderException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public TemplateLoaderException()
    {
        super();
    }

    public TemplateLoaderException(final Throwable t)
    {
        super(t);
    }

   public TemplateLoaderException(final String message)
    {
        super(message);
    }

    public TemplateLoaderException(final Throwable t, final String message)
    {
        super(message, t);
    }

    public TemplateLoaderException(final String message, final Object ... params)
    {
        super(String.format(message, params));
    }

    public TemplateLoaderException(final Throwable t, final String message, final Object ... params)
    {
        super(String.format(message, params), t);
    }
}
