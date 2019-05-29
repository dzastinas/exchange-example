# Goals
 + Exchange money
 + Error handling
 + Check if enough money
 + Show dialog after successful exchange
 + Let user select FROM: EUR, USD, JPY
 + Let user select TO: EUR, USD, JPY
 + Formatting by default Kotlin, klint https://ktlint.github.io/

# What could be improved if had more time:

    1. Room libruary implemented
    2. Would store all transaction and with balance snapshot
    3. Input validation would be done with InputFilter to let user correclty select input in the middle of 447.5555. 
    For time limits, right now you need to delete it from right 
    4. I deleted git history for reasons, but i commit dayly and often.
    5. Would improve UI, time consuming. But i can do it. 
    6. Add custom Enum and BigDecimal Moshi adapter, right now i am mapping it in interactor from String values
    
# Other requirements

    1. Kotlin was used
    2. Time spend. 4h + 4h + 6h = ~14h
    3. Lib used: Retrofit, RxJava, Data-binding, Moshi. Koin as dependency injection service. And my own lib for faster demo, shared classes. Source [https://gitlab.com/dzastinas/mini-list/tree/master/minilist]
    4. 1 Automation test -> ShowLoadBalanceTest.kt. Let's write test without instrumental tests. Ultimate goal.
    5. Used VMMV + MVI architecture. Easy to test data logic. View model and interactor(intent).
    6. For extendability VMMV + MVI architecture is easy. New rules could be applied in Exchange interactor
    7. Formatting was made with default Intellij Kotlin rules 
      
      
# Refs  
- My architecture demo in JUG http://kaunas-jug.lt/material/meetup46/JUG46_Faster_testing_with_Kotlin.pptx
- Repository for my personal demo. https://gitlab.com/dzastinas/mini-list
- Custom lib used is in https://gitlab.com/dzastinas/mini-list/tree/master/minilist
- Custom lib featured app published https://play.google.com/store/apps/details?id=net.justinas.minitemplate&hl=en
