class MyData : ArrayList<MyDataItem>(){
    data class MyDataItem(
        val body: String,
        val id: Int,
        val title: String,
        val userId: Int
    )
}