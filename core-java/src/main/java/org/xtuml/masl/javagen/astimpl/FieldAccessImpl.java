//
// UK Crown Copyright (c) 2011. All Rights Reserved.
//
package org.xtuml.masl.javagen.astimpl;

import org.xtuml.masl.javagen.ast.ASTNodeVisitor;
import org.xtuml.masl.javagen.ast.def.Field;
import org.xtuml.masl.javagen.ast.expr.Expression;
import org.xtuml.masl.javagen.ast.expr.FieldAccess;


public class FieldAccessImpl extends ExpressionImpl
    implements FieldAccess
{

  FieldAccessImpl ( final ASTImpl ast, final Expression instance, final Field field )
  {
    super(ast);
    setInstance(instance);
    setField(field);
  }

  FieldAccessImpl ( final ASTImpl ast, final Field field )
  {
    super(ast);
    setField(field);
  }

  @Override
  public <R, P> R accept ( final ASTNodeVisitor<R, P> v, final P p ) throws Exception
  {
    return v.visitFieldAccess(this, p);
  }

  @Override
  public void forceQualifier ()
  {
    if ( qualifier.get() == null && instance.get() == null )
    {
      if ( field.getModifiers().isStatic() )
      {
        setQualifier(new TypeQualifierImpl(getAST(), field.getDeclaringType()));
      }
      else
      {
        setInstance(new ThisImpl(getAST(), field.getParentTypeBody()));
      }
    }
  }

  @Override
  public FieldImpl getField ()
  {
    return field;
  }

  @Override
  public ExpressionImpl getInstance ()
  {
    if ( getEnclosingScope().requiresQualifier(this) )
    {
      forceQualifier();
    }
    return instance.get();
  }

  @Override
  public TypeQualifierImpl getQualifier ()
  {
    if ( getEnclosingScope().requiresQualifier(this) )
    {
      forceQualifier();
    }

    return qualifier.get();
  }

  @Override
  public FieldImpl setField ( final Field field )
  {
    this.field = (FieldImpl)field;
    return (FieldImpl)field;
  }


  @Override
  public ExpressionImpl setInstance ( Expression instance )
  {
    if ( ((ExpressionImpl)instance).getPrecedence() < getPrecedence() )
    {
      instance = getAST().createParenthesizedExpression(instance);
    }
    this.instance.set((ExpressionImpl)instance);
    return (ExpressionImpl)instance;
  }

  @Override
  protected int getPrecedence ()
  {
    return 15;
  }

  private void setQualifier ( final TypeQualifierImpl qualifier )
  {
    this.qualifier.set(qualifier);
  }

  private FieldImpl                          field;

  private final ChildNode<ExpressionImpl>    instance  = new ChildNode<ExpressionImpl>(this);


  private final ChildNode<TypeQualifierImpl> qualifier = new ChildNode<TypeQualifierImpl>(this);

}
