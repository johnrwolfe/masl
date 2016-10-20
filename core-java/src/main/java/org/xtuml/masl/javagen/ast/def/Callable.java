//
// UK Crown Copyright (c) 2011. All Rights Reserved.
//
package org.xtuml.masl.javagen.ast.def;

import java.util.List;

import org.xtuml.masl.javagen.ast.code.CodeBlock;
import org.xtuml.masl.javagen.ast.types.Type;


public interface Callable
{

  void setVisibility ( Visibility visibility );

  Visibility getVisibility ();

  boolean isVarArgs ();

  List<? extends Parameter> getParameters ();

  Parameter addParameter ( Parameter parameter );

  void setVarArgs ();

  Parameter addParameter ( Type type, String name );

  CodeBlock getCodeBlock ();

  CodeBlock setCodeBlock ( CodeBlock block );

  CodeBlock setCodeBlock ();

}
