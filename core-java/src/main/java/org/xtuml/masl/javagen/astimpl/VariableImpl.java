//
// UK Crown Copyright (c) 2011. All Rights Reserved.
//
package org.xtuml.masl.javagen.astimpl;


import java.util.EnumSet;

import org.xtuml.masl.javagen.ast.code.Variable;
import org.xtuml.masl.javagen.ast.def.Modifier;
import org.xtuml.masl.javagen.ast.expr.VariableAccess;
import org.xtuml.masl.javagen.ast.types.Type;


abstract class VariableImpl extends
    ASTNodeImpl
    implements Variable, ModifiersImpl.Filter
{

  VariableImpl ( final ASTImpl ast, final Type type, final String name )
  {
    super(ast);
    modifiers.set(new ModifiersImpl(ast, this));
    setName(name);
    setType(type);

  }


  VariableImpl ( final ASTImpl ast )
  {
    super(ast);
    modifiers.set(new ModifiersImpl(ast, this));
  }


  @Override
  public ModifiersImpl getModifiers ()
  {
    return modifiers.get();
  }

  @Override
  public String getName ()
  {
    return name;
  }

  @Override
  public TypeImpl getType ()
  {
    return type.get();
  }

  @Override
  public void setName ( final String name )
  {
    this.name = name;
  }

  @Override
  public void setType ( final Type type )
  {
    this.type.set((TypeImpl)type);
  }

  private final ChildNode<TypeImpl>      type      = new ChildNode<TypeImpl>(this);
  private final ChildNode<ModifiersImpl> modifiers = new ChildNode<ModifiersImpl>(this);
  private String                         name;

  @Override
  public VariableAccess asExpression ()
  {
    return getAST().createVariableAccess(this);
  }

  @Override
  public boolean isFinal ()
  {
    return getModifiers().isFinal();
  }

  @Override
  public void setFinal ()
  {
    getModifiers().setModifier(Modifier.FINAL);
  }

  @Override
  public EnumSet<Modifier> getImplicitModifiers ()
  {
    return EnumSet.noneOf(Modifier.class);
  }

}
