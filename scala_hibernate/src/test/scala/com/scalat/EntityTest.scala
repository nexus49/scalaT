package com.scalat

import junit.framework._
import org.hibernate.Session
import java.util.Date
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import junit.framework.Assert

object EntityTest {
	
    def suite: Test = {
        val suite = new TestSuite(classOf[EntityTest])
        suite
    }

    def main(args : Array[String]) {
        junit.textui.TestRunner.run(suite)
    }
}

/**
 * Unit test for simple Entity.
 */
class EntityTest extends TestCase {
	var sessionFactory = new Configuration().configure().buildSessionFactory()

	def testHibernate() {
		// create a couple of events...
		var session = sessionFactory.openSession()
		session.beginTransaction()
		session.save (new Event("Our very first event!", new Date()))
		session.save (new Event("A follow up event", new Date()))
		session.getTransaction().commit()
		session.close()

		// now lets pull events from the database and list them
		session = sessionFactory.openSession()
       session.beginTransaction()
       val result = session.createQuery( "from Event" ).list()
       var itr = result.iterator
       while(itr.hasNext){
    	   var event = itr.next
    	   var evt:Event = event.asInstanceOf[Event]
			System.out.println( "Event (" + evt.date + ") : " + evt.title )
		}
		
		val firstEvent:Event = result.iterator.next.asInstanceOf[Event]
		Assert.assertTrue(firstEvent.title.equals("Our very first event!") )
		
       session.getTransaction().commit()
       session.close()
	}

	override def tearDown() = if (sessionFactory != null)  { sessionFactory.close() }
}
