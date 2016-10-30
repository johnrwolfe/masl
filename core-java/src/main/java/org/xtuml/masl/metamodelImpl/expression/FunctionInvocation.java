//
// File: FunctionInvocation.java
//
// UK Crown Copyright (c) 2009. All Rights Reserved.
//
package org.xtuml.masl.metamodelImpl.expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.xtuml.masl.metamodelImpl.common.ParameterDefinition;
import org.xtuml.masl.metamodelImpl.common.Position;
import org.xtuml.masl.metamodelImpl.common.Service;
import org.xtuml.masl.metamodelImpl.error.SemanticError;
import org.xtuml.masl.metamodelImpl.type.BasicType;
import org.xtuml.masl.utils.TextUtils;


abstract class FunctionInvocation<ServiceType extends Service> extends CallExpression
{

  private final List<Expression> arguments;
  private final ServiceType      service;

  public FunctionInvocation ( final Position position, final ServiceType service, final List<Expression> arguments )
  {
    super(position);
    this.service = service;
    this.arguments = arguments;

    checkOutParams();

  }

  
  public List<Expression> getArguments ()
  {
    return Collections.unmodifiableList(arguments);
  }

  public ServiceType getService ()
  {
    return service;
  }

  /**
   * 
   * @return
   * @see org.xtuml.masl.metamodelImpl.expression.Expression#getType()
   */
  @Override
  public BasicType getType ()
  {
    return service.getReturnType();
  }

  protected abstract String getCallPrefix ();

  @Override
  public String toString ()
  {
    return getCallPrefix() + "(" + TextUtils.formatList(arguments, "", ", ", "") + ")";
  }


  @Override
  protected List<Expression> getFindArgumentsInner ()
  {
    final List<Expression> params = new ArrayList<Expression>();

    for ( final Expression argument : arguments )
    {
      params.addAll(argument.getFindArguments());
    }
    return params;

  }


  @Override
  protected List<FindParameterExpression> getFindParametersInner ()
  {
    final List<FindParameterExpression> params = new ArrayList<FindParameterExpression>();

    for ( final Expression argument : arguments )
    {
      params.addAll(argument.getConcreteFindParameters());
    }
    return params;

  }

  @Override
  public int getFindAttributeCount ()
  {
    int count = 0;
    for ( final Expression argument : arguments )
    {
      count += argument.getFindAttributeCount();
    }
    return count;
  }

  protected List<Expression> getFindSkeletonArguments ()
  {
    final List<Expression> newArgs = new ArrayList<Expression>(arguments.size());
    for ( int i = 0; i < arguments.size(); ++i )
    {
      final Expression newArg = arguments.get(i).getFindSkeleton();
      newArgs.add(newArg);
      if ( newArg instanceof FindParameterExpression )
      {
        ((FindParameterExpression)newArg).overrideType(getService().getParameter(i).getType());
      }
    }

    return newArgs;
  }

  protected void checkOutParams ()
  {
    int i = 0;
    for ( final ParameterDefinition parameter : service.getParameters() )
    {
      if ( parameter.getMode() == ParameterDefinition.Mode.OUT )
      {
        final Expression arg = arguments.get(i);
        try
        {
          arg.checkWriteable(arg.getPosition());
        }
        catch ( final SemanticError e )
        {
          e.report();
        }
      }
      ++i;
    }
  }

  @Override
  public abstract boolean equals ( Object obj );

  @Override
  public int hashCode ()
  {
    return service.hashCode() ^ arguments.hashCode();
  }

  @Override
  public List<Expression> getChildExpressions ()
  {
    return new ArrayList<Expression>(arguments);
  }

}
