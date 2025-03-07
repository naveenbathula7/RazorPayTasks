import com.razorpay.razorpaytasks.data.RazorTask
import com.razorpay.razorpaytasks.data.RazorTaskDao
import com.razorpay.razorpaytasks.network.RazorRetrofitInstance
import kotlinx.coroutines.flow.Flow

class RazorTaskRepository(private val dao: RazorTaskDao) {

    val allTasks: Flow<List<RazorTask>> = dao.getAllTasks()

    suspend fun fetchTasksFromAPI() {
        val taskList = RazorRetrofitInstance.api.getTasks()
        taskList.forEach { dao.insertTask(it) }
    }

    suspend fun insertTask(task: RazorTask) {
        dao.insertTask(task)
    }

    suspend fun updateTask(task: RazorTask) {
        dao.updateTask(task)
    }
}