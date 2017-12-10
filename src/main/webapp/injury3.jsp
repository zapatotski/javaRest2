<%@ page import="java.math.BigInteger"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.*" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.ObjectInputStream" %>
<%@ page import="java.io.ObjectOutputStream" %>
<%@ page import="java.io.Serializable" %>
<%@ page import="java.util.*" %>
<%@ page import="live.Game" %>
<%@ page import="live.Player" %>


<%! 

File dir = new File(".");

public File[] sortFile(File[] fs){
	Arrays.sort(fs);
	return fs;
}

public Map<String,List<Game>> deserialization(File f){
	FileInputStream fInput=null;
	ObjectInputStream oInput=null;
	Map<String,List<Game>> q=null;
	try{
		fInput=new FileInputStream(f);
		oInput=new ObjectInputStream(fInput);
		q=(Map<String,List<Game>>) oInput.readObject();
	}
	catch(Exception e){
		throw new RuntimeException(e);
	}
	return q;
}

public Map<String,List<Game>> addLastGames(Map<String,List<Game>> m, File[] files){
	for(int i=files.length-4;i>files.length-7;i--){
		Map<String, List<Game>> lastlist=deserialization(files[i]);
		for(String s:lastlist.keySet()){
			if(m.containsKey(s)){
				for(Game g:lastlist.get(s)){
					int day=(new Date().getDate()-(files.length-i));
					int month=new Date().getMonth()+1;
					if(day<0){
						day=32-(files.length-i);
						month=new Date().getMonth();
					}
					
					String strDay="";
					if(day<10)
						strDay="0"+day;
					else
						strDay=""+day;
					String strMonth="";
					if(month<10)
						strMonth="0"+month;
					else
						strMonth=""+month;
					g.time=strDay+"."+strMonth;
					m.get(s).add(g);
				}
			}
		}
	}
	return m;
}
    
%>

<head>
	<meta charset="utf-8">
	<title>Карточки и замены</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="js/bootstrap.min.js">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script>
		!function ($) {
		
			  "use strict"; // jshint ;_;
		
		
			 /* COLLAPSE PUBLIC CLASS DEFINITION
			  * ================================ */
		
			  var Collapse = function (element, options) {
			    this.$element = $(element)
			    this.options = $.extend({}, $.fn.collapse.defaults, options)
		
			    if (this.options.parent) {
			      this.$parent = $(this.options.parent)
			    }
		
			    this.options.toggle && this.toggle()
			  }
		
			  Collapse.prototype = {
		
			    constructor: Collapse
		
			  , dimension: function () {
			      var hasWidth = this.$element.hasClass('width')
			      return hasWidth ? 'width' : 'height'
			    }
		
			  , show: function () {
			      var dimension
			        , scroll
			        , actives
			        , hasData
		
			      if (this.transitioning) return
		
			      dimension = this.dimension()
			      scroll = $.camelCase(['scroll', dimension].join('-'))
			      actives = this.$parent && this.$parent.find('> .accordion-group > .in')
		
			      if (actives && actives.length) {
			        hasData = actives.data('collapse')
			        if (hasData && hasData.transitioning) return
			        actives.collapse('hide')
			        hasData || actives.data('collapse', null)
			      }
		
			      this.$element[dimension](0)
			      this.transition('addClass', $.Event('show'), 'shown')
			      this.$element[dimension](this.$element[0][scroll])
			    }
		
			  , hide: function () {
			      var dimension
			      if (this.transitioning) return
			      dimension = this.dimension()
			      this.reset(this.$element[dimension]())
			      this.transition('removeClass', $.Event('hide'), 'hidden')
			      this.$element[dimension](0)
			    }
		
			  , reset: function (size) {
			      var dimension = this.dimension()
		
			      this.$element
			        .removeClass('collapse')
			        [dimension](size || 'auto')
			        [0].offsetWidth
		
			      this.$element[size !== null ? 'addClass' : 'removeClass']('collapse')
		
			      return this
			    }
		
			  , transition: function (method, startEvent, completeEvent) {
			      var that = this
			        , complete = function () {
			            if (startEvent.type == 'show') that.reset()
			            that.transitioning = 0
			            that.$element.trigger(completeEvent)
			          }
		
			      this.$element.trigger(startEvent)
		
			      if (startEvent.isDefaultPrevented()) return
		
			      this.transitioning = 1
		
			      this.$element[method]('in')
		
			      $.support.transition && this.$element.hasClass('collapse') ?
			        this.$element.one($.support.transition.end, complete) :
			        complete()
			    }
		
			  , toggle: function () {
			      this[this.$element.hasClass('in') ? 'hide' : 'show']()
			    }
		
			  }
		
		
			 /* COLLAPSIBLE PLUGIN DEFINITION
			  * ============================== */
		
			  $.fn.collapse = function (option) {
			    return this.each(function () {
			      var $this = $(this)
			        , data = $this.data('collapse')
			        , options = typeof option == 'object' && option
			      if (!data) $this.data('collapse', (data = new Collapse(this, options)))
			      if (typeof option == 'string') data[option]()
			    })
			  }
		
			  $.fn.collapse.defaults = {
			    toggle: true
			  }
		
			  $.fn.collapse.Constructor = Collapse
		
		
			 /* COLLAPSIBLE DATA-API
			  * ==================== */
		
			  $(function () {
			    $('body').on('click.collapse.data-api', '[data-toggle=collapse]', function ( e ) {
			      var $this = $(this), href
			        , target = $this.attr('data-target')
			          || e.preventDefault()
			          || (href = $this.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, '') //strip for ie7
			        , option = $(target).data('collapse') ? 'toggle' : $this.data()
			      $(target).collapse(option)
			    })
			  })
		
			}(window.jQuery);
	</script>

<script type="text/javascript">

setInterval(function(){
	var nod=document.getElementsByName("pan");
	for(var i=0;i<nod.length;i++){
	  if(nod[i].style.height != "0px")
		  nod[i].style.height='auto';
	}
	},3000);

</script>
</head>
<body>
	<div class="container" style="margin-top:20px;">
		<div class="header">
                        <a href="index.html" style="padding:10px;border:1px solid black;display:inline-block;color:black;font-weight:bold;" onmouseover="this.style.backgroundColor='#808080';this.style.color='white';" onmouseout="this.style.backgroundColor='#ffffff';this.style.color='black';">Плотный график</a>
                        <a href="#" style="margin-left:-5px;background:#808080;color:white;padding:10px;border:1px solid black;display:inline-block;font-weight:bold;">Замены по травме</a>
			<div style="float:right"><i>Последнее обновление данных:
				<%   
				FileReader reader = new FileReader(new File("obnovinjury.txt"));
				String s="";
				try {
				           // читаем посимвольно
				            int c;
				            while((c=reader.read())!=-1){
				                 
				                s+=(char)c;
				            } 
				        }
				        catch(IOException ex){
				             
				            System.out.println(ex);
				        }
				out.print(new Date(Long.valueOf(s)+10800000).toLocaleString());
				%> 
			</i></div>
		</div>
		<nav class="navbar navbar-default">
			<ul class="nav navbar-nav">
		        <li><a href="/injury.jsp">Сегодня</a></li>
		        <li><a href="/injury1.jsp">Вчера</a></li>
		        <li><a href="/injury2.jsp">Позавчера</a></li>
		        <li class="active"><a href="#">3 дня назад</a></li>
		        <li><a href="/injury4.jsp">4 дня назад</a></li>
		        <li><a href="/injury5.jsp">5 дней назад</a></li>
		        <li><a href="/injury6.jsp">6 дней назад</a></li>
	        </ul>
		</nav>
		<div class="content">
		<div style="clear:both"></div>
			<%		
			File[] files = dir.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.toLowerCase().endsWith(".inj");
			    }
			});
			Map<String,List<Game>> map=deserialization(sortFile(files)[files.length>5 ? files.length-3:0]);
			map=addLastGames(map,sortFile(files));
			Set key=map.keySet();
			
			for(Object k:key){
				out.println("<h4 style=\"display: inline-block;\">"+k+"</h4>");
				%>
				     <span class="panel">
				       <span class="panel-heading">
				          <a role="button" data-toggle="collapse" data-parent="#accordion" href="#<%out.print(k.hashCode());%>">
				           Info
				          </a>
				       </span>
				<%
				List<Game> lg=map.get(k);
				boolean b=true;
				for(Game g:lg){
					String score="-";
					if(!"".equals(g.htScore))
						score=g.htScore;
					if(!"".equals(g.ftScore))
						score=g.ftScore;
					
					
					List<Player> y1=new ArrayList();
					List<Player> r1=new ArrayList();
					List<Player> yr1=new ArrayList();
					List<Player> s1=new ArrayList();
					List<Player> y2=new ArrayList();
					List<Player> yr2=new ArrayList();
					List<Player> r2=new ArrayList();
					List<Player> s2=new ArrayList();
					
					if(g.time.toCharArray()[2]=='.' && b){
					%>
					<div id="<%out.print(k.hashCode());%>" name="pan" class="panel-collapse collapse" style="height:0px;">
					   <div class="panel-body">
					<%
					b=false;
					}
					
					out.println("<div style=\"text-align:center;\"><span style=\"float:left;\">"+g.time+"</span>");
					
					
					for(Integer idy:g.yellow1.keySet()){
						out.println("<img src=\"http://s1.swimg.net/gsmf/583/img/events/YC.png\" title=\""+g.yellow1.get(idy)+"\" alt=\"Yellow card\" width=\"11\" height=\"11\">");
						y1.add(g.yellow1.get(idy));
					}
					for(Integer idyr:g.yellowred1.keySet()){
						out.println("<img src=\"http://s1.swimg.net/gsmf/583/img/events/Y2C.png\" title=\""+g.yellowred1.get(idyr)+"\" alt=\"YellowRed card\" width=\"11\" height=\"11\">");
						yr1.add(g.yellowred1.get(idyr));
					}
				    for(Integer idr:g.red1.keySet()){
						out.println("<img src=\"http://s1.swimg.net/gsmf/583/img/events/RC.png\" title=\""+g.red1.get(idr)+"\" alt=\"Red card\" width=\"11\" height=\"11\">");
						r1.add(g.red1.get(idr));
				    }
					for(Integer ids:g.subs1.keySet()){
						out.println("<img src=\"http://s1.swimg.net/gsmf/583/img/events/SO.png\" title=\""+g.subs1.get(ids)+"\" alt=\"Out\" width=\"11\" height=\"11\">");
						s1.add(g.subs1.get(ids));
					}
					out.println(" "+g.team1+" "+score+" "+g.team2+" ");
					for(Integer idy:g.yellow2.keySet()){
						out.println("<img src=\"http://s1.swimg.net/gsmf/583/img/events/YC.png\" title=\""+g.yellow2.get(idy)+"\" alt=\"Yellow card\" width=\"11\" height=\"11\">");
						y2.add(g.yellow2.get(idy));
					}
					for(Integer idyr:g.yellowred2.keySet()){
						out.println("<img src=\"http://s1.swimg.net/gsmf/583/img/events/Y2C.png\" title=\""+g.yellowred2.get(idyr)+"\" alt=\"YellowRed card\" width=\"11\" height=\"11\">");
						yr2.add(g.yellowred2.get(idyr));
					}
					for(Integer idr:g.red2.keySet()){
						out.println("<img src=\"http://s1.swimg.net/gsmf/583/img/events/RC.png\" title=\""+g.red2.get(idr)+"\" alt=\"Red card\" width=\"11\" height=\"11\">");
						r2.add(g.red2.get(idr));
					}
					for(Integer ids:g.subs2.keySet()){
						out.println("<img src=\"http://s1.swimg.net/gsmf/583/img/events/SO.png\" title=\""+g.subs2.get(ids)+"\" alt=\"Out\" width=\"11\" height=\"11\">"); 
						s2.add(g.subs2.get(ids));
					}
					%>
				     <span class="panel">
				       <span class="panel-heading">
				          <a role="button" data-toggle="collapse" data-parent="#accordion" href="#<%out.print(g.toString());%>">
				           Info
				          </a>
				       </span>
				       <div id="<%out.print(g.toString());%>" class="panel-collapse collapse">
				         <div class="panel-body">
				           <div style="position:relative;margin-top:15px; margin-bottom:15px;">
				           <%
				           out.println("<div class=\"row\"><div class=\"span6\"><table class=\"table table-condensed table-bordered\">");
				           %>
				           <tr>
				               <th style="font-size:12px;">Name</th>
				               <th style="font-size:12px;">P</th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/minute_played.png" alt="Minutes played" title="Minutes played" width="12" height="12"></th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/appearance.png" alt="Appearances" title="Appearances" width="12" height="12"></th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/L.png" alt="Lineups" title="Lineups" width="12" height="12"></th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/G.png" alt="Goal" title="Goal" width="12" height="12"></th>				           
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/YC.png" alt="Yellow card" title="Yellow card" width="12" height="12"></th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/Y2C.png" alt="YellowRed card" title="Yellow card" width="12" height="12"></th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/RC.png" alt="Red card" title="Red card" width="12" height="12"></th>
				           </tr>
				           <%
				           for(Player p:y1)
				        	   out.println("<tr><td><img src=\"http://s1.swimg.net/gsmf/583/img/events/YC.png\" alt=\"Yellow card\" width=\"11\" height=\"11\">"+p.name+"</td><td>"+p.pos+"</td><td>"+p.min+"</td><td>"+p.appear+"</td><td>"+p.lineup+"</td><td>"+p.goal+"</td><td>"+p.yellow+"</td><td>"+p.yellowred+"</td><td>"+p.red+"</td></tr>");
				           for(Player p:yr1)
				        	   out.println("<tr><td><img src=\"http://s1.swimg.net/gsmf/583/img/events/Y2C.png\" alt=\"YellowRed card\" width=\"11\" height=\"11\">"+p.name+"</td><td>"+p.pos+"</td><td>"+p.min+"</td><td>"+p.appear+"</td><td>"+p.lineup+"</td><td>"+p.goal+"</td><td>"+p.yellow+"</td><td>"+p.yellowred+"</td><td>"+p.red+"</td></tr>");
				           for(Player p:r1)
				        	   out.println("<tr><td><img src=\"http://s1.swimg.net/gsmf/583/img/events/RC.png\" alt=\"Red card\" width=\"11\" height=\"11\">"+p.name+"</td><td>"+p.pos+"</td><td>"+p.min+"</td><td>"+p.appear+"</td><td>"+p.lineup+"</td><td>"+p.goal+"</td><td>"+p.yellow+"</td><td>"+p.yellowred+"</td><td>"+p.red+"</td></tr>");
				           for(Player p:s1)
				        	   out.println("<tr><td><img src=\"http://s1.swimg.net/gsmf/583/img/events/SO.png\" alt=\"Subs\" width=\"11\" height=\"11\">"+p.name+"</td><td>"+p.pos+"</td><td>"+p.min+"</td><td>"+p.appear+"</td><td>"+p.lineup+"</td><td>"+p.goal+"</td><td>"+p.yellow+"</td><td>"+p.yellowred+"</td><td>"+p.red+"</td></tr>");
				           out.println("</table></div><div class=\"span6\"><table class=\"table table-condensed table-bordered\">");
				           %>				         
				           <tr>
				               <th style="font-size:12px;">Name</th>
				               <th style="font-size:12px;">P</th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/minute_played.png" alt="Minutes played" title="Minutes played" width="12" height="12"></th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/appearance.png" alt="Appearances" title="Appearances" width="12" height="12"></th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/L.png" alt="Lineups" title="Lineups" width="12" height="12"></th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/G.png" alt="Goal" title="Goal" width="12" height="12"></th>				           
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/YC.png" alt="Yellow card" title="Yellow card" width="12" height="12"></th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/Y2C.png" alt="YellowRed card" title="Yellow card" width="12" height="12"></th>
					           <th><img src="http://s1.swimg.net/gsmf/583/img/events/RC.png" alt="Red card" title="Red card" width="12" height="12"></th>
			               </tr>
				           <%
				           for(Player p:y2)
				        	   out.println("<tr><td><img src=\"http://s1.swimg.net/gsmf/583/img/events/YC.png\" alt=\"Yellow card\" width=\"11\" height=\"11\">"+p.name+"</td><td>"+p.pos+"</td><td>"+p.min+"</td><td>"+p.appear+"</td><td>"+p.lineup+"</td><td>"+p.goal+"</td><td>"+p.yellow+"</td><td>"+p.yellowred+"</td><td>"+p.red+"</td></tr>");
				           for(Player p:yr2)
				        	   out.println("<tr><td><img src=\"http://s1.swimg.net/gsmf/583/img/events/Y2C.png\" alt=\"YellowRed card\" width=\"11\" height=\"11\">"+p.name+"</td><td>"+p.pos+"</td><td>"+p.min+"</td><td>"+p.appear+"</td><td>"+p.lineup+"</td><td>"+p.goal+"</td><td>"+p.yellow+"</td><td>"+p.yellowred+"</td><td>"+p.red+"</td></tr>");
				           for(Player p:r2)
				        	   out.println("<tr><td><img src=\"http://s1.swimg.net/gsmf/583/img/events/RC.png\" alt=\"Red card\" width=\"11\" height=\"11\">"+p.name+"</td><td>"+p.pos+"</td><td>"+p.min+"</td><td>"+p.appear+"</td><td>"+p.lineup+"</td><td>"+p.goal+"</td><td>"+p.yellow+"</td><td>"+p.yellowred+"</td><td>"+p.red+"</td></tr>");
				           for(Player p:s2)
				        	   out.println("<tr><td><img src=\"http://s1.swimg.net/gsmf/583/img/events/SO.png\" alt=\"Subs\" width=\"11\" height=\"11\">"+p.name+"</td><td>"+p.pos+"</td><td>"+p.min+"</td><td>"+p.appear+"</td><td>"+p.lineup+"</td><td>"+p.goal+"</td><td>"+p.yellow+"</td><td>"+p.yellowred+"</td><td>"+p.red+"</td></tr>");
				           out.println("</table></div></div>");
				           %>				           
				           </div>
				         </div>
				       </div>
				   </span>
				   <% 
					out.println("</div>");
				}
				if(!b)
					out.println("</div></div>");
				out.println("</span>");
			}
			%>
		</div>
	</div>
	
</body>