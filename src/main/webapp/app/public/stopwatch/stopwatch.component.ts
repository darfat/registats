import { Component, Vue, Inject, Prop, Watch} from 'vue-property-decorator';

@Component
export default class Stopwatch extends Vue {
  @Prop(String)  matchTime: string | undefined

    public time: string = '00:00:00'
    public timeBegan : Date = null
    public timeStopped = null
    public stoppedDuration: number = 0
    public started : any = null
    public running: boolean = false;

    created () {
        console.log('created.....');
    }
    mounted () {
        console.log('mounted.....');
        this.init();
    }

    public init(){
       this.$root.$on('getTime',() =>{
         this.getTime();
       });
    }

    public getTime() {
      console.log('get time...');
    }
    public start() {
      if(this.running) return;
      
      if (this.timeBegan === null) {
        this.reset();
        this.timeBegan = new Date();
      }
    
      if (this.timeStopped !== null) {
        this.stoppedDuration += (Date.now() - this.timeStopped);
      }
    
      this.started = setInterval(this.clockRunning, 1000);	
      this.running = true;
    }
    
    public stop() {
      this.running = false;
      this.timeStopped = new Date();
      clearInterval(this.started);
    }
    
    public reset() {
      this.running = false;
      clearInterval(this.started);
      this.stoppedDuration = 0;
      this.timeBegan = null;
      this.timeStopped = null;
      this.time = "00:00:00";
    }
    
    public clockRunning(){
      let currentTime: Date = new Date();  
      let timeElapsed: Date = new Date(Date.now() - this.timeBegan.valueOf()  - this.stoppedDuration)
      let hour = timeElapsed.getUTCHours()
      let min = timeElapsed.getUTCMinutes()
      let sec = timeElapsed.getUTCSeconds()
      let ms = timeElapsed.getUTCMilliseconds();
    
      this.time = 
        this.zeroPrefix(hour, 2) + ":" + 
        this.zeroPrefix(min, 2) + ":" + 
        this.zeroPrefix(sec, 2) + "."; 
       // this.zeroPrefix(ms, 3);

    };
    
    public zeroPrefix(num, digit) {
      var zero = '';
      for(var i = 0; i < digit; i++) {
        zero += '0';
      }
      return (zero + num).slice(-digit);
    }
    
}