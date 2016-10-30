//
// File: FunctionCall.java
//
// UK Crown Copyright (c) 2006. All Rights Reserved.
//
package org.xtuml.masl.cppgen;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.xtuml.masl.utils.TextUtils;



public class MacroCall extends Expression
{

  /**
   * Creates a macro call using the supplied arguments
   * 

   *          the macro to call

   *          the arguments to pass
   */
  public MacroCall ( final Macro macro, final List<MacroArgument> arguments )
  {
    this.macro = macro;
    this.arguments = arguments;
  }

  @Override
  String getCode ( final Namespace currentNamespace, final String alignment )
  {
    final StringBuilder buf = new StringBuilder();
    buf.append(macro.getName());
    final List<String> argCode = new ArrayList<String>();
    for ( final MacroArgument arg : arguments )
    {
      argCode.add(arg.getCode(currentNamespace));
    }

    buf.append("(" + TextUtils.formatList(argCode, "", ",", "") + ")\n");

    return buf.toString();
  }

  /**
   * Macros are starnge in that they can appear anywhere in a code file. This
   * makes the call look like a declaration so it can be added outside a
   * function definition.
   * 
   * @return a pseudo-declaration containing the macro call.
   */
  Declaration getDeclaration ()
  {
    return new Declaration()
    {

      @Override
      Set<CodeFile> getIncludes ()
      {
        return MacroCall.this.getIncludes();
      }

      /* Not really a declaration, but make it appear as such to the code file */
      @Override
      void writeDeclaration ( final Writer writer, final String indent, final Namespace currentNamespace ) throws IOException
      {
        writer.write(indent + getCode(currentNamespace, ""));
      }

      @Override
      void writeForwardDeclaration ( final Writer writer, final String indent, final Namespace currentNamespace ) throws IOException
      {
      }

    };
  }


  @Override
  Set<Declaration> getForwardDeclarations ()
  {
    final Set<Declaration> result = super.getForwardDeclarations();
    for ( final MacroArgument arg : arguments )
    {
      result.addAll(arg.getForwardDeclarations());
    }
    return result;
  }

  @Override
  Set<CodeFile> getIncludes ()
  {
    final Set<CodeFile> result = super.getIncludes();

    result.add(macro.getDeclaredIn());
    for ( final MacroArgument arg : arguments )
    {
      result.addAll(arg.getIncludes());
    }
    return result;
  }

  @Override
  int getPrecedence ()
  {
    return 0;
  }

  private final Macro               macro;

  private final List<MacroArgument> arguments;

}
