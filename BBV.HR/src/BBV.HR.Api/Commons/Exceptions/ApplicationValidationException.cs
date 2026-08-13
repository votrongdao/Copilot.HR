using System;
using System.Collections.Generic;

public sealed class ApplicationValidationException : Exception
{
    public ApplicationValidationException(IDictionary<string, string[]> errors)
        : base("One or more validation failures have occurred.")
    {
        Errors = errors;
    }

    public IDictionary<string, string[]> Errors { get; }
}
