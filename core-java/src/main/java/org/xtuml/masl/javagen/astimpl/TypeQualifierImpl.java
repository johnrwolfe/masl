//
// UK Crown Copyright (c) 2011. All Rights Reserved.
//
package org.xtuml.masl.javagen.astimpl;

import org.xtuml.masl.javagen.ast.ASTNodeVisitor;
import org.xtuml.masl.javagen.ast.def.TypeDeclaration;
import org.xtuml.masl.javagen.ast.expr.TypeQualifier;


public class TypeQualifierImpl extends QualifierImpl
    implements TypeQualifier
{

  public TypeQualifierImpl ( final ASTImpl ast, final TypeDeclaration type )
  {
    super(ast);
    if ( type == null )
    {
      throw new IllegalArgumentException("Cannot qualify anonymous type");
    }
    this.type = (TypeDeclarationImpl)type;
  }

  @Override
  public TypeDeclarationImpl getTypeDeclaration ()
  {
    return type;
  }

  @Override
  public <R, P> R accept ( final ASTNodeVisitor<R, P> v, final P p ) throws Exception
  {
    return v.visitTypeQualifier(this, p);
  }

  private final TypeDeclarationImpl type;

  @Override
  public void forceQualifier ()
  {
    if ( type.getDeclaringCompilationUnit() != null )
    {
      qualifier.set(new PackageQualifierImpl(getAST(), type.getEnclosingPackage()));
    }
    else
    {
      qualifier.set(new TypeQualifierImpl(getAST(), type.getDeclaringType()));
    }
  }

  @Override
  public QualifierImpl getQualifier ()
  {
    if ( qualifier.get() == null && getEnclosingScope().requiresQualifier(type) )
    {
      forceQualifier();
    }
    return qualifier.get();
  }


  private final ChildNode<QualifierImpl> qualifier = new ChildNode<QualifierImpl>(this);
}
