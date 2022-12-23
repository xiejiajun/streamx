/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.streampark.flink.core.test

import org.apache.streampark.flink.core.SqlSplitter
import org.scalatest.funsuite.AnyFunSuite

class FlinkSqlSplitterSuite extends AnyFunSuite {


  test("splitAntiSlashAndQuoteSql") {
    val sql = "SELECT '\\'' as col1 -- some comments \nFROM Tbl"
    val sqlSegments = SqlSplitter.splitSql(sql)
    val replacedSql = sqlSegments.head.sql
    assert(replacedSql.equals("SELECT '\\'' as col1 \nFROM Tbl"))
  }

  test("splitMultiAntiSlashSql") {
    val sql = "SELECT '\\\\' as col1 -- some comments \nFROM Tbl"
    val sqlSegments = SqlSplitter.splitSql(sql)
    val replacedSql = sqlSegments.head.sql
    assert(replacedSql.equals("SELECT '\\\\' as col1 \nFROM Tbl"))
  }

  test("splitSimpleSql") {
    val sql = "SELECT 'simpleColumn' as col1 -- some comments \nFROM Tbl"
    val sqlSegments = SqlSplitter.splitSql(sql)
    val replacedSql = sqlSegments.head.sql
    assert(replacedSql.equals("SELECT 'simpleColumn' as col1 \nFROM Tbl"))
  }
}
