function initOrganization(data){
	var st = [];
	var a=0;
	$.each(data,function(i,sts){
		if($.inArray(sts.parentDepartNo, st)==-1){
			st[a++]=sts.parentDepartNo;
		}
	});
	function treeMenu(a){
		this.tree=a||[];
		this.groups={};
	};
	treeMenu.prototype={
			init:function(){
				this.group();
				return this.getDom(this.groups[st[0]]);
			},
			group:function(){
				for(var i=0;i<this.tree.length;i++){
					if(this.groups[this.tree[i].parentDepartNo]){
						this.groups[this.tree[i].parentDepartNo].push(this.tree[i]);
					}else{
						this.groups[this.tree[i].parentDepartNo]=[];
						this.groups[this.tree[i].parentDepartNo].push(this.tree[i]);
					}
				}
			},
			getDom:function(a){
				if(!a){return ''}
				if(a[0].parentDepartNo==''){
					var html='\n<ul id="org" style="display:none">\n';
				}
				else{
					var html='\n<ul>\n';
				}
				for(var i=0;i<a.length;i++){
					html+='<li>'+a[i].departmentName;
					html+=this.getDom(this.groups[a[i].departmentNo]);
					html+='</li>\n';
				};
				html+='</ul>\n';
				return html;
			}
	};
	var str=new treeMenu(data).init();
	$('#org_div').html(str);
	$("#org").jOrgChart({
		chartElement : '#chart',
		dragAndDrop  : false
	});
}
