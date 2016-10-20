/*
 * Filename : Database.java
 * 
 * UK Crown Copyright (c) 2008. All Rights Reserved
 */
package org.xtuml.masl.translate.sql.main;

import org.xtuml.masl.cppgen.Class;
import org.xtuml.masl.cppgen.CodeFile;
import org.xtuml.masl.cppgen.Expression;
import org.xtuml.masl.cppgen.Function;
import org.xtuml.masl.cppgen.Library;
import org.xtuml.masl.cppgen.Literal;
import org.xtuml.masl.cppgen.Namespace;
import org.xtuml.masl.cppgen.TypeUsage;
import org.xtuml.masl.cppgen.Variable;
import org.xtuml.masl.translate.main.Architecture;


public class Database
{
  private static Library sqllib = new Library("sql").inBuildSet(Architecture.buildSet);

  static public final CodeFile oneToOneMapperInc        = sqllib.createInterfaceHeader("sql/RelationshipBinaryDefinitions.hh");
  static public final CodeFile oneToManyMapperInc       = sqllib.createInterfaceHeader("sql/RelationshipBinaryDefinitions.hh");
  static public final CodeFile manyToOneMapperInc       = sqllib.createInterfaceHeader("sql/RelationshipBinaryDefinitions.hh");
  static public final CodeFile assocOneToOneMapperInc   = sqllib.createInterfaceHeader("sql/RelationshipTenaryDefinitions.hh");
  static public final CodeFile assocOneToManyMapperInc  = sqllib.createInterfaceHeader("sql/RelationshipTenaryDefinitions.hh");
  static public final CodeFile assocManyToOneMapperInc  = sqllib.createInterfaceHeader("sql/RelationshipTenaryDefinitions.hh");
  static public final CodeFile assocManyToManyMapperInc = sqllib.createInterfaceHeader("sql/RelationshipTenaryDefinitions.hh");

  static public final Variable  mapperInstance              = new Variable("mapper");

  static public final CodeFile  utilInc                     = sqllib.createInterfaceHeader("sql/Util.hh");
  static public final CodeFile  exceptionInc                = sqllib.createInterfaceHeader("sql/Exception.hh");
  static public final CodeFile  populationInc               = sqllib.createInterfaceHeader("sql/Population.hh");
  static public final CodeFile  objectMapperInc             = sqllib.createInterfaceHeader("sql/ObjectMapper.hh");
  static public final CodeFile  objectMapperItrInc          = sqllib.createInterfaceHeader("sql/ObjectMapperItr.hh");
  static public final CodeFile  objectSqlGeneratorInc       = sqllib.createInterfaceHeader("sql/ObjectSqlGenerator.hh");
  static public final CodeFile  RelationshipSqlGeneratorInc = sqllib.createInterfaceHeader("sql/RelationshipSqlGenerator.hh");
  static public final CodeFile  ResourceMonitorContextInc   = sqllib.createInterfaceHeader("sql/ResourceMonitorObserver.hh");


  static public final CodeFile  schemaInc                   = sqllib.createInterfaceHeader("sql/Schema.hh");
  static public final CodeFile  criteriaInc                 = sqllib.createInterfaceHeader("sql/Criteria.hh");
  static public final CodeFile  assignerStateMapperInc      = sqllib.createInterfaceHeader("sql/AssignerStateMapper.hh");

  public static final Namespace namespace                   = new Namespace("SQL");
  public static final Class     schemaClass                 = new Class("Schema", namespace, schemaInc);
  public static final Class     criteriaClass               = new Class("Criteria", namespace, criteriaInc);
  public static final Class     cacheTypeClass              = new Class("CacheType", namespace, objectSqlGeneratorInc);
  public static final Class     resourceMonitorContextClass = new Class("ResourceMonitorContext",
                                                                        namespace,
                                                                        ResourceMonitorContextInc);


  // The classes that are generated by the SQL framework inherit from generic
  // base classes that define a series of nested Object classes that will be
  // in scope for the implementations of the generated dervied class methods.
  // Therefore set the namespace to null in their class definitions below.
  public static final Class     psObjectClass               = new Class("PsObject", null, objectMapperInc);
  public static final Class     psObjectPtrClass            = new Class("PsObjectPtr", null, objectMapperInc);
  public static final Class     psObjectPtrSetClass         = new Class("PsObjectPtrSet", null, objectMapperInc);
  public static final Class     psBaseObjectPtrSwaSetClass  = new Class("PsBaseObjectPtrSwaSet", null, objectMapperInc);
  public static final Class     psBaseObjectPtrClass        = new Class("PsBaseObjectPtr", null, objectMapperInc);
  public static final Class     psCachedPtrMapClass         = new Class("PsCachedPtrMap", null, objectMapperInc);

  private static final CodeFile                              objectSqlHeader                = sqllib.createInterfaceHeader("sql/ObjectSql.hh");
  private static final CodeFile                              objectSqlRepositoryHeader      = sqllib.createInterfaceHeader("sql/ObjectSqlRepository.hh");
  private static final CodeFile                              relationsipSqlHeader           = sqllib.createInterfaceHeader("sql/RelationshipSql.hh");
  private static final CodeFile                              relationsipSqlRepositoryHeader = sqllib.createInterfaceHeader("sql/RelationshipSqlRepository.hh");

  public static final Class                                 objectSql                      = new Class("ObjectSql",
                                                                                                        new Namespace("SQL"),
                                                                                                        objectSqlHeader);
  public static final Class                                 relationshipSql                = new Class("RelationshipSql",
                                                                                                        new Namespace("SQL"),
                                                                                                        relationsipSqlHeader);
  public static final Class                                 objectSqlRepository            = new Class("ObjectSqlRepository",
                                                                                                        new Namespace("SQL"),
                                                                                                        objectSqlRepositoryHeader);
  public static final Class                                 relationshipSqlRepository       = new Class("RelationshipSqlRepository",
                                                                                                        new Namespace("SQL"),
                                                                                                        relationsipSqlRepositoryHeader);
  private final DatabaseTraits  databaseTraits;

  public Database ( final DatabaseTraits databaseTraits )
  {
    this.databaseTraits = databaseTraits;
  }

  public DatabaseTraits getDatabaseTraits ()
  {
    return databaseTraits;
  }

  public Class getObjectSqlGeneratorClass ( final Class baseObject, final Class derivedObject )
  {
    final Class sqlGenClass = new Class("ObjectSqlGenerator", namespace, objectSqlGeneratorInc);
    sqlGenClass.addTemplateSpecialisation(new TypeUsage(baseObject));
    sqlGenClass.addTemplateSpecialisation(new TypeUsage(derivedObject));
    return sqlGenClass;
  }

  static public Function getStringToValueFn ()
  {
    final Function stringToValueFn = new Function("stringToValue", namespace, utilInc);
    return stringToValueFn;
  }

  static public Function getConvertToColumnValueFn ()
  {
    final Function convertToColumnValueFn = new Function("convertToColumnValue", namespace, utilInc);
    return convertToColumnValueFn;
  }

  static public Function getValueToStringFn ()
  {
    final Function valueToStringFn = new Function("valueToString", namespace, utilInc);
    return valueToStringFn;
  }

  public Class getObjectMapperClass ()
  {
    // The ObjectMapper class uses template specialisation so
    // need to return a new instance of the mapper rather than
    // just referencing it directly like the other class declarations
    // above.
    return new Class("ObjectMapper", namespace, objectMapperInc);
  }

  public Class getPopulationClass ()
  {
    // The Population class uses template specialisation so
    // need to return a new instance of the mapper rather than
    // just referencing it directly like the other class declarations
    // above.
    return new Class("SqlPopulation", namespace, populationInc);
  }

  public Expression isAssignerSetFnCall ( final String assignerKey )
  {
    final Class assignerStateMapper = new Class("AssignerStateMapper", namespace, assignerStateMapperInc);
    final Expression singletonFnCall = assignerStateMapper.callStaticFunction("singleton");
    return new Function("isAssignerSet").asFunctionCall(singletonFnCall, false, new Expression[]
      { Literal.createStringLiteral(assignerKey) });
  }

  public Expression setAssignerStateFnCall ( final String assignerKey, final Expression stateValue )
  {
    final Class assignerStateMapper = new Class("AssignerStateMapper", namespace, assignerStateMapperInc);
    final Expression singletonFnCall = assignerStateMapper.callStaticFunction("singleton");
    return new Function("setAssignerState").asFunctionCall(singletonFnCall, false, new Expression[]
      { Literal.createStringLiteral(assignerKey), stateValue });
  }

  public Expression getAssignerStateFnCall ( final TypeUsage enumType, final String assignerKey )
  {
    final Class assignerStateMapper = new Class("AssignerStateMapper", namespace, assignerStateMapperInc);
    final Expression singletonFnCall = assignerStateMapper.callStaticFunction("singleton");

    final Function getAssignerStateFn = new Function("getAssignerState");
    getAssignerStateFn.addTemplateSpecialisation(enumType);

    return getAssignerStateFn.asFunctionCall(singletonFnCall, false, new Expression[]
      { Literal.createStringLiteral(assignerKey) });
  }

}
