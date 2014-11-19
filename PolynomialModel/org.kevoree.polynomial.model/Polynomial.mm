class polymer.Cloud  {
    @contained
    instances : polymer.VmInstance[0,*]
    @contained
    softwares : polymer.Software[0,*]
}
class polymer.VmInstance  {
    cpu : Double
    price : Double
    name : String
    @contained
    tasks : polymer.Task[0,*]
}
class polymer.Software  {
    cpuh : Double
    name : String
    @id
    id: Int
}
class polymer.Task  {
    weight : Int
    cpu : Double
    time : Double
    software : polymer.Software
}
