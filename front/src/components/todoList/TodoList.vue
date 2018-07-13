<template>


<div>
     <input class="my-input" v-model="inputContent" placeholder="Take to do"  v-on:keyup.enter="onEnter"/>
     <ul class="todolist">
         <li v-for="list in listContents">
               {{list.content}}
          </li>
      </ul>
</div>


</template>

<script>
    import vPageTitle from '../common/pageTitle.vue';




    export default {
        components:{
            vPageTitle
        },
        data(){
            return{
                inputContent:'',
                listContents:[
                    {content:'What do you think?',isDone:false},
                    {content:'Have fun with blue admin',isDone:false},
                    {content:'Write email to business cat',isDone:false},
                    {content:'Get in touch with jspang team',isDone:false},
                    {content:'Ei cum soleet appareat,ex est graeci medioc',isDone:false},
                    {content:'Simaul erroribus ad usu',isDone:false},
                    {content:'Vim an eius ocurreret abhorreant,id name a',isDone:false}

                ]
            }
        },

        methods: {
            onEnter:function(){

                if(this.inputContent!=''){

                    var listContent={content:this.inputContent,isDone:true};
                    this.listContents.splice(0,0,listContent);
                    this.inputContent='';
                }
            }
        }
    }
</script>

<style>



</style>
