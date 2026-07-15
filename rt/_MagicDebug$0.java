package base;
import static base.Util.*;
import java.sql.*;

public interface _MagicDebug$0 extends Sealed$0{
  default Object imm$$hash$1(Object p0){
    System.out.print(toS(p0)+"\n"); //Crucially the above does not use println since that makes \r\n on win
    // try{Db.main();}
    //catch(Throwable t){t.printStackTrace();}
    return Void$0.instance;//Another version with different compile options could use .err
  }
  _MagicDebug$0 instance= new _MagicDebug$0(){};
}
/*
 This shows that SQL lite can work on running fearlesses,
 will run the API later
class Db{
  public static void main() throws Exception{
    Class.forName("org.sqlite.JDBC");
    try (var c= java.sql.DriverManager.getConnection("jdbc:sqlite:data.db")) {
      setupConnection(c);
      c.setAutoCommit(false);
      setupSchema(c);
      insertData(c);
      readData(c);
      testForeignKey(c);
      testRollback(c);
      c.commit();
      System.out.println("sqlite test: ok");
    }
  }

  static void setupConnection(java.sql.Connection c) throws Exception{
    exec(c,"PRAGMA foreign_keys = ON");
    exec(c,"PRAGMA journal_mode = WAL");
    checkOne(c,"PRAGMA foreign_keys","1");
  }

  static void setupSchema(java.sql.Connection c) throws Exception{
    exec(c,"DROP TABLE IF EXISTS book");
    exec(c,"DROP TABLE IF EXISTS author");
    exec(c,"CREATE TABLE author("
      +"id INTEGER PRIMARY KEY,"
      +"name TEXT NOT NULL UNIQUE)");
    exec(c,"CREATE TABLE book("
      +"id INTEGER PRIMARY KEY,"
      +"author_id INTEGER NOT NULL REFERENCES author(id),"
      +"title TEXT NOT NULL,"
      +"year INTEGER NOT NULL,"
      +"pages INTEGER NOT NULL CHECK(pages > 0))");
  }

  static void insertData(java.sql.Connection c) throws Exception{
    try (var ps= c.prepareStatement("INSERT INTO author(id,name) VALUES (?,?)")) {
      author(ps,1,"Ada Lovelace");
      author(ps,2,"Alan Turing");
      author(ps,3,"Grace Hopper");
    }
    try (var ps= c.prepareStatement(
      "INSERT INTO book(id,author_id,title,year,pages) VALUES (?,?,?,?,?)"
    )) {
      book(ps,1,1,"Notes on the Analytical Engine",1843,66);
      book(ps,2,2,"Computing Machinery and Intelligence",1950,27);
      book(ps,3,2,"On Computable Numbers",1936,36);
      book(ps,4,3,"The Education of a Computer",1952,21);
    }
  }

  static void readData(java.sql.Connection c) throws Exception{
    var sql= """
      SELECT a.name,b.title,b.year,b.pages
      FROM book b JOIN author a ON a.id = b.author_id
      ORDER BY b.year,b.id
      """;
    try (var ps= c.prepareStatement(sql); var rs= ps.executeQuery()) {
      int count= 0;
      while(rs.next()){
        count += 1;
        System.out.println(
          rs.getInt("year")+" | "+
          rs.getString("name")+" | "+
          rs.getString("title")+" | "+
          rs.getInt("pages")+" pages"
        );
      }
      check(count == 4,"Expected 4 books, got "+count);
    }
    checkOne(c,"SELECT COUNT(*) FROM author","3");
    checkOne(c,"SELECT COUNT(*) FROM book","4");
    checkOne(c,"SELECT SUM(pages) FROM book","150");
  }

  static void testForeignKey(java.sql.Connection c) throws Exception{
    try (var ps= c.prepareStatement(
      "INSERT INTO book(id,author_id,title,year,pages) VALUES (?,?,?,?,?)"
    )) {
      bookNoExec(ps,99,999,"Impossible Book",2026,1);
      ps.executeUpdate();
      throw new AssertionError("Foreign key check did not fail");
    }
    catch(java.sql.SQLException ok){
      System.out.println("foreign key failure detected: "+ok.getClass().getSimpleName());
    }
  }

  static void testRollback(java.sql.Connection c) throws Exception{
    var sp= c.setSavepoint();
    exec(c,"INSERT INTO author(id,name) VALUES (10,'Temporary Author')");
    checkOne(c,"SELECT COUNT(*) FROM author WHERE id = 10","1");
    c.rollback(sp);
    checkOne(c,"SELECT COUNT(*) FROM author WHERE id = 10","0");
    System.out.println("rollback test: ok");
  }

  static void author(java.sql.PreparedStatement ps, int id, String name) throws Exception{
    ps.setInt(1,id);
    ps.setString(2,name);
    ps.executeUpdate();
  }

  static void book(
    java.sql.PreparedStatement ps, int id, int authorId, String title, int year, int pages
  ) throws Exception{
    bookNoExec(ps,id,authorId,title,year,pages);
    ps.executeUpdate();
  }

  static void bookNoExec(
    java.sql.PreparedStatement ps, int id, int authorId, String title, int year, int pages
  ) throws Exception{
    ps.setInt(1,id);
    ps.setInt(2,authorId);
    ps.setString(3,title);
    ps.setInt(4,year);
    ps.setInt(5,pages);
  }

  static void exec(java.sql.Connection c, String sql) throws Exception{
    try (var s= c.createStatement()) { s.executeUpdate(sql); }
  }

  static void checkOne(java.sql.Connection c, String sql, String expected) throws Exception{
    try (var s= c.createStatement(); var rs= s.executeQuery(sql)) {
      check(rs.next(),"No result for "+sql);
      var actual= rs.getString(1);
      check(expected.equals(actual),"Expected "+expected+" got "+actual+" for "+sql);
      check(!rs.next(),"More than one result for "+sql);
    }
  }

  static void check(boolean b, String msg){
    if (!b){ throw new AssertionError(msg); }
  }
}*/