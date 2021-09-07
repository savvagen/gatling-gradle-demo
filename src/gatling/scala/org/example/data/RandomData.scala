package org.example.data

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.javafaker.Faker
import com.google.gson.Gson
import org.example.models.{Comment, Post, Task, Todo, User}

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, OffsetDateTime}
import java.util
import java.util.Locale


class RandomData {

  var faker = new Faker(new Locale("en_us"))
  var objectMapper = new ObjectMapper()
  var gson = new Gson()

  var datetimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  var dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def dateTimeNow(): OffsetDateTime = {
    OffsetDateTime.parse(OffsetDateTime.now().format(datetimeFormat))
  }

  def randomCategory(): String = {
    val categories = Array("cats", "dogs").toList
    categories(scala.util.Random.nextInt(categories.size))
  }

  def randomUser(): String = {
    objectMapper.writeValueAsString(new User()
      .setName(s"${faker.name().firstName()} ${faker.name().lastName()}")
      .setUsername(faker.name().username())
      .setEmail(faker.internet().emailAddress())
    )
  }

  def randomComment(): String = {
    objectMapper.writeValueAsString(new Comment()
      .setPost(faker.random().nextInt(1,100))
      .setName(s"Test comment ${faker.random().nextInt(10000000, 99999999)}_${LocalDateTime.now()}")
      .setEmail(faker.internet().emailAddress())
      .setLikes(util.Arrays.asList(faker.random().nextInt(1, 100)))
      .setDislikes(util.Arrays.asList(faker.random().nextInt(1, 100)))
      .setBody(s"Hello There. Hello to User ${faker.internet().emailAddress()}")
      setCreatedAt(dateTimeNow().toString)
    )
  }

  def randomPost(): String = {
    objectMapper.writeValueAsString(new Post()
      .setTitle(s"New Post ${faker.random().nextInt(999, 1000)}")
      .setSubject("Test")
      .setBody(s"Hello This is a post ${faker.random().nextInt(1, 100)}")
      .setCategory(randomCategory())
      .setUser(1)
      .setComments(util.Arrays.asList(1, faker.random().nextInt(2, 100), faker.random().nextInt(2, 100)))
      .setCreatedAt(dateTimeNow().toString)
    )
  }

  def randomToDo(): String = {
    objectMapper.writeValueAsString(new Todo()
      .setUser(faker.random().nextInt(1,100))
      .setTitle(s"Todo ${faker.random().nextInt(1000000, 9999999)}")
      .setCompleted(true)
      .setCreatedAt(dateTimeNow().toString)
      .setTasks(util.Arrays.asList(
        new Task()
          .setId(1)
          .setDescription(s"ToDo Task ${faker.random().nextInt(10000)}")
          .setCompleted(true)
          .setStartTime(dateTimeNow().toString)
          .setEndTime(dateTimeNow().plusNanos((1 * 1000000) * 500).toString)
      ))
    )

  }

  def randomTask(): String = {
    objectMapper.writeValueAsString(new Task()
      .setTodo(faker.random().nextInt(1,100))
      .setDescription(s"Task ${faker.random().nextInt(1000000, 9999999)}")
      .setCompleted(true)
      .setStartTime(dateTimeNow().toString)
      .setEndTime(dateTimeNow().plusNanos((1 * 1000000) * 500).toString)
    )
  }


  def generateTodo(tasksNumber: Int = 3): Todo = {
    // Generate Tasks
    val tasks = new util.ArrayList[Task]()
    for (i <- 0 until tasksNumber){
      val task = new Task()
        .setTodo(faker.random().nextInt(100,1000))
        .setDescription(s"Task ${faker.random().nextInt(1000000, 9999999)}")
        .setStartTime(dateTimeNow().toString)
        .setCompleted(true)
        .setEndTime(dateTimeNow().plusNanos((1 * 1000000) * 500).toString)
      if (i % 2 == 0) {
        task.setCompleted(false)
      }
      tasks.add(i, task)
    }
    // Generate Post
    gson.fromJson(randomToDo(), classOf[Todo])
      .setTasks(tasks)
  }

}
