# Cold Sweat
* Different base temperature: https://mikul.gitbook.io/cold-sweat/
  * Cave dragon: +30?
  * Sea dragon: -30?
  * Forest dragon: ?

``` java
// Needs to be re-set on death?
TempHelper.addTemperature(player, new Temperature(50), Temperature.Types.BASE);
```

Give Ice / Fire resistance instead?
Forest dragon -> if below 0 add 15, if above 0 subtract 15

# Configuration
Option to disable mixin?