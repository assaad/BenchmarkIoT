//KMF_VERSION=4.18.2
//VERSION=1.0.0-SNAPSHOT
class org.kevoree.modeling.bench.SmartSystem {
    rooms : org.kevoree.modeling.bench.Room[0,*]
}

class org.kevoree.modeling.bench.Sensor {
    name : String
}

class org.kevoree.modeling.bench.DiscreteSensor : org.kevoree.modeling.bench.Sensor {
    value : Double
}

class org.kevoree.modeling.bench.PolynomialSensor : org.kevoree.modeling.bench.Sensor {
    @precision(0.0001)
    value : Double
}

class org.kevoree.modeling.bench.Room {
    name : String
    @contained
    sensors : org.kevoree.modeling.bench.PolynomialSensor
}
