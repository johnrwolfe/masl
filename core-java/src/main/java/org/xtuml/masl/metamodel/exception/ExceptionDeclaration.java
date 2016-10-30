//
// File: ExceptionDeclaration.java
//
// UK Crown Copyright (c) 2008. All Rights Reserved.
//
package org.xtuml.masl.metamodel.exception;

import org.xtuml.masl.metamodel.ASTNode;
import org.xtuml.masl.metamodel.common.PragmaList;
import org.xtuml.masl.metamodel.common.Visibility;
import org.xtuml.masl.metamodel.domain.Domain;


public interface ExceptionDeclaration
    extends ASTNode
{

  String getName ();

  Domain getDomain ();

  Visibility getVisibility ();

  PragmaList getPragmas ();

}
