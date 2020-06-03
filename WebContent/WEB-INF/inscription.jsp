<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>INSCRIPTION</title>
</head>
 <c:if test="${!empty session}">
<c:import url="/${deconnexionboutonpage}" var="file" scope="page" />
    ${file}
    </c:if>
           <c:if test="${!empty session}">
         <div id="menu">
<c:import url="/${menupage}" var="file" scope="page" />
    ${file}
    </div> 
    </c:if>
  
<body>
<c:import url="/menu" var="file" scope="page" />
        <form method="post">
        
            <fieldset>
                <legend>Créer vos identifiants de connexion</legend>
                <br/>
               
                <label for="login">Login <span class="requis">*</span></label>
                <input type="text" id="login" name="login" value="" size="32" maxlength="32" />
                <div class="erreur" id="erreurlogin"></div>
                <br />
                <br />
                
                 <label for="nom">Nom <span class="requis">*</span></label>
                <input type="text" id="nom" name="nom" value="" size="20" maxlength="20" />
                <div class="erreur" id="erreurnom"></div>
                <br />
                <br />
                
                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="56" maxlength="56" />
                 <div class="erreur" id="erreurmotdepasse"></div>      
                <br />
                <br />
                
                    <label for="confirmation">Confirmer le mot de passe <span class="requis">*</span></label>
                <input type="password" id="confirmation" name="confirmation" value="" size="56" maxlength="56" />
                 <div class="erreur" id="erreurconfirmation"></div>      
                <br />
                <br />
                
                   <label for="email">Email<span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="" size="60" maxlength="60" />
                 <div class="erreur" id="erreuremail"></div>      
                <br />
                <br />
                
                <input type="submit" value="Inscription" class="sansLabel" id="submit" /> 
                <input type="reset" value="Effacer saisie"  id="reset"/>          
                <br />
                        <div class="erreur"><c:out value="${inscriptionform.creationAutorisee}"/>
                         <br/>
                         <c:forEach var="entry" items="${inscriptionform.erreurs}" varStatus="status">
                         <c:out value="${entry}"/>
                         <br/>
                         </c:forEach>
                         </div> 
                        
                            
            </fieldset>
        </form>  
<script src="<c:url value='/js/inscription.js'/>" type="text/javascript"></script>
</body>
</html>