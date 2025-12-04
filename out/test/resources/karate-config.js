function fn(){

    karate.configure('connectTimeout', 15000);
    karate.configure('readTimeout', 15000);
    karate.configure('ssl',true);

   return {
        api:{
            uri: 'https://artifact-clue-detector.onrender.com'
        }

    }
}