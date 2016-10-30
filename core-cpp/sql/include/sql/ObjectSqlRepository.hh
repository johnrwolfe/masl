//============================================================================//
// UK Crown Copyright (c) 2009. All rights reserved.
//
// File: ObjectSql.hh
//
//============================================================================//
#ifndef Sql_ObjectSqlRepository_HH
#define Sql_ObjectSqlRepository_HH

#include <map>
#include <string>

namespace SQL {

// *****************************************************************
//! Define an repository that can be used to programatically determine
//! the SQL mapping that have been used for a MASl object. 
// *****************************************************************
class ObjectSql;
class ObjectSqlRepository
{
   public:
      static ObjectSqlRepository& getInstance();

      bool registerObjectSql   (const ObjectSql* const sql);
      void deregisterObjectSql (const ObjectSql* const sql);

      const ObjectSql& getObjectSql(const std::string& domainName, const std::string& objectName) const;

   private:
      ObjectSqlRepository();
     ~ObjectSqlRepository();

   private:
      ObjectSqlRepository(const ObjectSqlRepository& rhs);
      ObjectSqlRepository& operator=(const ObjectSqlRepository& rhs);

   private:
      std::map<std::string, const ObjectSql* const> repository;
};

} // end namespace SQL

#endif

