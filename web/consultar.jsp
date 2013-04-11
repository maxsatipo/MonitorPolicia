<%@page import="java.util.List"%>
<%  
List<String> mensajes = (List<String>)session.getAttribute("mensajes");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Monitoreo Policial</title>
    </head>
    <body>
        <h1>Monitoreo Policial</h1>
        <form action="ConsultaMensaje" method="post">
            <input type="submit" name="consultar" value="Consultar"/>
                    </form>

           <form action="ConsultaMensaje" method="post" >
               <input type="submit" name="reset" value="Limpiar"/>
                    </form>
            <%if(mensajes!=null){
            for(String mensaje:mensajes ){
            %>
            <%= mensaje %>
            <br>
            <%}}%>
            
            
            
        
        
        
    </body>
</html>
