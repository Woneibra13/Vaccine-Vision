package application;

import java.util.ArrayList;
import java.sql.*;
public class RankingSystem {
    private int size = 0;
    private Entry head;
    private Connection conn = null;
    /**
     * Creates an empty ranking system
     */
    public RankingSystem() {
         this.head = null;
        try {
        	conn = DriverManager.getConnection("jdbc:sqlite:ranklog.db");
        	Statement stm = conn.createStatement();
        	stm.executeUpdate("create table if not exists ranklog(rank integer, name string, region string, email string, age integer, conditions integer)");
        	System.out.println("DB Created!");
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        finally
        {
          try
          {
            if(conn != null)
              conn.close();
            
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
    }

    /**
     * Creates a ranking system from a head
     * @param head
     */
    public RankingSystem(Entry head) {
        this.head = head;
        try {
        	conn = DriverManager.getConnection("jdbc:sqlite:ranklog.db");
        	Statement stm = conn.createStatement();
        	stm.executeUpdate("create table if not exists ranklog(rank integer, name string, region string, email string, age integer, conditions integer)");
        }
        catch(SQLException e) {
        	System.out.print(e.getMessage());
        }
        finally
        {
          try
          {
            if(conn != null)
              conn.close();
            
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
    }

    /**
     * Getter for head field
     * @return head
     */
    public Entry getHead() {
        return head;
    }

    public void setHead(Entry head) {
        this.head = head;
    }
    /**
     * Getter for size field
     * @return head
     */
    public int size() {
        return size;
    }
    
    public User searchByName(String name) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:ranklog.db");
            PreparedStatement pstm = conn.prepareStatement("select * from ranklog where name == ?");
            pstm.setString(1, name);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()) {
                return new User( rs.getString("name") ,rs.getInt("age"), rs.getString("region"), rs.getString("email"));
            }
        }
         catch(SQLException e) {
             e.printStackTrace();
         }
         finally
         {
           try
           {
             if(conn != null)
               conn.close();
             
           }
           catch(SQLException e)
           {
             // connection close failed.
             System.err.println(e.getMessage());
           }
         }
        return null;
    }
    
    /**
     * Finds the rank of a given user - Wrapper function of the private method contains()
     * @param user
     * @return Rank of user
     */
    public int rankOf(User user) {
       int rank = 0;
    	try {
    		conn = DriverManager.getConnection("jdbc:sqlite:ranklog.db");
        	PreparedStatement pstm = conn.prepareStatement("select rank from ranklog where name = ? and region = ? and email = ? and age = ? and conditions = ?");
        	pstm.setString(1, user.getName());
        	pstm.setString(2, user.getRegion());
        	pstm.setString(3, user.getEmail());
        	pstm.setInt(4, user.getAge());
        	pstm.setInt(5, user.getReqs() - user.getAge());
        	ResultSet rs = pstm.executeQuery();
        	while(rs.next()) {
        		rank = rs.getInt("rank");
        	}
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
    	finally
        {
          try
          {
            if(conn != null)
              conn.close();
            
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
        return rank;
    }

    /**
     * Adds user into Ranking system based on their priority - Wrapper function of insert()
     * Updates ranklog
     * @param user
     * @return rank of recently added user
     */
    public void addUser(User user) {
    	if (user == null)
    		return;
        try {
        	//check if database already contains the user 
        	conn = DriverManager.getConnection("jdbc:sqlite:ranklog.db");
        	PreparedStatement pstm = conn.prepareStatement("select rank from ranklog where name = ? and region = ? and email = ? and age = ? and conditions = ?");
        	pstm.setString(1, user.getName());
        	pstm.setString(2, user.getRegion());
        	pstm.setString(3, user.getEmail());
        	pstm.setInt(4, user.getAge());
        	pstm.setInt(5, user.getReqs() - user.getAge());
        	ResultSet rs = pstm.executeQuery();
        	if(rs.next()) {
        		//delete the entry
        		pstm = conn.prepareStatement("delete from ranklog where name = ?");
	        	pstm.setString(1, user.getName());
	        	pstm.executeUpdate();
        	}
        	//insert into ranking system
        	int userRank = insert(new Entry(user, null));;
        	
        	//insert recently added user into ranklog
        	pstm = conn.prepareStatement("insert into ranklog values(?,?,?,?,?,?)");
        	pstm.setInt(1, userRank);
        	pstm.setString(2, user.getName());
        	pstm.setString(3, user.getRegion());
        	pstm.setString(4, user.getEmail());
        	pstm.setInt(5, user.getAge());
        	pstm.setInt(6, user.getReqs() - user.getAge());
        	pstm.executeUpdate();
        	System.out.println("Added " + user.getName() + " at rank " + userRank);
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        finally
        {
          try
          {
            if(conn != null)
              conn.close();
            
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
    }

    /**
     * Searches through ranking system for the entry at the given rank - Wrapper function of entryAt()
     * @param rank
     * @return entry result of search query
     */
   public Entry entryAtRank(int rank) {
       return entryAt(rank);
    }

    /**
     * Private method that inserts an entry into the ranking system based on its priority
     * @param toInsert
     * @return rank of inserted entry
     */
 private int insert(Entry toInsert) {
	   int rank = 0;
       if (this.size==0) {
    	   this.setHead(toInsert);
		   size++;
		   rank++;
       }
       else if(this.size==1) {
    	   this.getHead().setNext(toInsert);
    	   size++;
		   rank=size;
       }
			/*
			 * if (this.contains(toInsert.getUser()) < 0) return -1;
			 */
       else {
            Entry curr = this.getHead();
            while (curr != null) {
                if (toInsert.getUser().compareTo(curr.getUser()) > 0) {
                	//new head case
                	if(curr == this.getHead()) {
                		this.setHead(toInsert);
                		toInsert.setNext(curr);
                		this.size++;
                		return 1;
                	}
                	else{
                		toInsert.setNext(curr.getNext());
                		curr.setNext(toInsert);
                		this.size++;
                		return ++rank;
                	}
                }
                if(curr.getNext()==null) {
                	curr.setNext(toInsert);
                	return ++size;
                }
                rank++;
                curr = curr.getNext();
            }
        }
		return rank;
    }

    /**
     * Private method that searches for an entry at the given index
     * @param index
     * @return Entry at the given index
     */
   private Entry entryAt(int index) {
        if (index < 0)
            return null;
        else if (index == 0)
            return this.getHead();
        Entry curr = this.getHead();
        while (curr.getNext() != null) {
            if (index == 0) {
                break;
            }
            curr = curr.getNext();
            index--;
        }
        return curr;
    }
    /**
     * Private Method that removes a given Entry from the ranking system
     * @param toDelete
     * @return removed User
     */
   private void remove(Entry toDelete) {
       if(toDelete == null)
        	return;
        Entry curr = this.getHead();
        while(curr.getNext()!=null) {
        	if(curr.getNext().getUser().equals(toDelete.getUser())) {
        		curr.setNext(curr.getNext().getNext()); 
       		 	size--; 
       		 	return; 
       		 }
        	curr = curr.getNext();
        }
    }

    /**
     * Displays all entries in the ranking system in the format of:
     * <rank>) Name = <user's name>, Region = <user's region>, Age = <user's age>, Email = <user's email>
     */
    public void display(){
        Entry curr = getHead();
        int rank = 0;
        while(curr!=null){
            System.out.println(++rank + ") Name = " + curr.getUser().getName() + ", Region = " + curr.getUser().getRegion() + ", Age = " + curr.getUser().getName() + ", Email = " + curr.getUser().getEmail());
            curr = curr.getNext();
        }
   }
   //converts ranklog.db to an ArrayLists of strings which all have the format of
   //<rank>) Name = <user's name>, Region = <user's region>, Age = <user's age>, Email = <user's email>
   public ArrayList<String> DBtoStrArrList() {
    	ArrayList<String> rv = new ArrayList<String>();
    	try {
    		conn = DriverManager.getConnection("jdbc:sqlite:ranklog.db");
    		Statement stm = conn.createStatement();
    		ResultSet rs = stm.executeQuery("select * from ranklog");
    		rs.beforeFirst();
    		while(rs.next()) {
    			rv.add(rs.getInt("rank") + rs.getString("name") + rs.getString("region") + rs.getString("email") + rs.getInt("age") + + rs.getInt("conditions"));
    		}
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	finally
        {
          try
          {
            if(conn != null)
              conn.close();
            
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
    	return rv;
    }
    public ArrayList<User> DBtoUserArrayList() {
    	ArrayList<User> arr = new ArrayList<User>();
    	try {
    		conn = DriverManager.getConnection("jdbc:sqlite:ranklog.db");
    		Statement stm = conn.createStatement();
    		ResultSet rs = stm.executeQuery("select * from ranklog");
    		while(rs.next()) {
    			arr.add(new User(rs.getString("name") ,rs.getInt("age"),  rs.getString("region") , rs.getString("email")));
    		}
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	finally
        {
          try
          {
            if(conn != null)
              conn.close();
            
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
    	return arr;
    }
    
    public void wipe() {
    	try {
    		conn = DriverManager.getConnection("jdbc:sqlite:ranklog.db");
    		Statement stm = conn.createStatement();
    		stm.executeUpdate("drop table if exists ranklog");
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	finally
        {
          try
          {
            if(conn != null)
              conn.close();
            
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
    }
}
