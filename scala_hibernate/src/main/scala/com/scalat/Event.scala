package com.scalat;

import java.util.Date
import scala.reflect.BeanProperty

class Event(@BeanProperty var title:String, @BeanProperty var date:Date){
	@BeanProperty var id: Long = _
	def this()={
		this(null, new Date())
	}
}