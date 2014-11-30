ShoebillPlusPlus
================
An enhanced and <a href="https://github.com/Bukkit/Bukkit/">bukkit</a>-like API for <a href="https://github.com/Shoebill">Shoebill</a>

Example Usage
================
This shows an example usage for the scheduler framework:
```java
// Executes the given runnable every 200 Ticks. 
// 1 Tick = 50ms, so it will print the message every 10 seconds
PlusServer.get().getScheduler().runTaskTimerAsynchronously(SinkGamemode.getInstance(), new PlusRunnable() { 
  int i = 1;
  @Override
  public void run() {
    PlusServer.get().getLogger().info("Scheduler Test: #" + i);
    i++;
  }
}, 0, 200); 
```
<br/>
<b>Output:</b><br/>
<img src="http://puu.sh/dbJcx/2df8596743.png"/>

Contribution Guidelines
==============
* Same as <a href="https://github.com/SpongePowered/Sponge/blob/master/CONTRIBUTING.md">Sponge's Contributing Guidelines</a>
* We compile with Java 8, so use the Java 8 Coding Standards.


License
==============
This work is licensed under an GNU Affero General Public Licenese v3. For more information, read the LICENSE file.


To-Do
================
- [x] Implement new event system
- [x] Implement new scheduler system
- [ ] Implement new command system
- [ ] Implement new plugin system/wrapper(?)
- [ ] Add wiki and explain how to use this
