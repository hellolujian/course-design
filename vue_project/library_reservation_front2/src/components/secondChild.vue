<template>
	<div id="secondChild">
		<h2>secondChild组件</h2>
		<p>从firstChild接受的字符串参数：{{msg}}</p>

	</div>

</template>
<script>
	import bus from '../assets/eventBus';
	export default{
	    data(){
            return {
                msg:"默认值"
            }
        },
	    mounted() {
            var self = this;
            bus.$on("userDefinedEvent",function(msg){
                self.msg=msg;
            });
	    }
	}
</script>