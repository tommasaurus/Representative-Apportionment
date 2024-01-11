## Description on how to use your code
1. Run `./gradlew build`
2. Execute `java -jar ./build/libs/Apportionment.jar ./build/libs/<filename> <flags>`
   
   3. There are three valid flags (optional):
   
      4. `--reps` or `-r` argument is an integer > 0
      
      5. `--format` or `-f` argument is `alpha` for alphabetical sort or `benefit` for relative benefit sort
   
      6. `--algorithm` or `-a` argument is `jefferson`, `hamilton`, or `huntington`

