function fn(){

    karate.configure('connectTimeout', 15000);
    karate.configure('readTimeout', 15000);
    karate.configure('ssl',true);

   return {
        api:{
            uri: 'http://localhost:8080'
        }

    }
}