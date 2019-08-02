<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Connexion</title>
        <style>
            :root {
                --colorText: #A0A0A0;
                --arierPlanPage: #101010;
                --arierPlanElement: #202020;
                --premierPlan: #404040;
                --premierPlan2: #606060;
                --bordur: #000000;
            }
            body {
                font-family: Arial, sans-serif;
                font-size: 100%;
                display: block;
                margin: 0;
                min-width: 308px;
                background-color: var(--arierPlanPage);
                overflow: hidden;
                color: var(--colorText);
            }
            header{
                height:34px;
                background-color: var(--arierPlanElement);
                border-bottom: var(--bordur) solid 1px;
                padding: 8px;
                overflow: hidden;
                text-align: center;

            }
            .ip{
                font-size: 160%;
            }
            .tooltip {
                position: relative;
                display: inline-block;
            }
            .tooltip .tooltiptext {
                font-size: 63%;
                visibility: hidden;
                width: 140px;
                background-color: #555;
                color: #fff;
                text-align: center;
                border-radius: 6px;
                padding: 5px;
                position: absolute;
                z-index: 1;
                top: 0;
                left: calc( 100% + 90px );
                margin-left: -75px;
                opacity: 0;
                transition: opacity 0.3s;
            }
            .tooltip .tooltiptext::after {
                content: "";
                position: absolute;
                left: -10px;
                top: 50%;
                margin-top: -5px;
                border-width: 5px;
                border-style: solid;
                border-color: transparent #555 transparent transparent;
            }
            .tooltip:hover .tooltiptext {
                visibility: visible;
                opacity: 1;
            }
            .select{
                background-color: var(--premierPlan2)!important;
            }
            div.div{
                border-radius: 10px;
                margin-left:auto;
                margin-right:auto;
                width: 80%;
                margin-top: 30px;
                padding: 10px;
                text-align: center;
                background-color: var(--arierPlanElement);
                border: 1px solid var(--bordur);
            }
            form{
                margin-bottom: 0;
                display: block;
                margin-top: 0;
            }
            h1{
                text-align: center;
                font-size: 110%;
                font-style: normal;
                font-weight: normal;
                text-decoration: underline;
                margin-top: 0;
                margin-bottom: 10px;
            }
            div.formConect{
                text-align: left;
                margin: 2px;
                font-family: Arial, sans-serif;
                font-size: 100%;
                height: 30px;
                width: 300px;
                cursor: auto;
                border-radius: 16px;
                border: 0;
                overflow: hidden;
                background-color: transparent;
            }
            div.formConectAll{
                text-align: center;
                margin-top: 0;
                padding: 0;
                font-family: Arial, sans-serif;
                font-size: 100%;
                height: auto;
                width: 304px;
                cursor: auto;
                margin-left: auto;
                margin-right: auto;
                border-radius: 16px;
                border: 0;
                overflow: hidden;
                background-color: var(--premierPlan);
            }
            input.formConect{
                color: var(--colorText);
                width: 280px;
                margin-left: 10px;
                margin-right: 10px;
                border: 0;
                margin-top: 0;
                font-family: Arial, sans-serif;
                font-size: 100%;
                height: 29px;
                background-color: transparent;
                outline: none;
                padding:0;
                border-bottom: 1px solid var(--colorText);
            }
            input.formConectValid{
                color: var(--colorText);
                width: 280px;
                margin-left: 10px;
                margin-right: 10px;
                border: 0;
                padding: 0;
                margin-top: 0;
                font-family: Arial, sans-serif;
                font-size: 100%;
                height: 30px;
                background-color: transparent;
                outline: none;
            }
            input::placeholder{
                margin-top: 0;
                margin-bottom: 0;
                color: var(--premierPlan2);
            }
            input:hover::placeholder{
                color: var(--colorText);
            }
        </style>
    </head>
    <body>
    <header>
        <span class="ip">IP : <span class="tooltip" onclick="coppy()" onmouseout="outcoppy()">docjyj.ddns.net<span class="tooltiptext" id="myTooltip">Copy to clipboard</span></span></span>
        <script>
            function coppy() {
                navigator.clipboard.writeText("docjyj.ddns.net");
                document.getElementById("myTooltip").innerHTML = "Copié !";
            }
            function outcoppy() {
                document.getElementById("myTooltip").innerHTML = "Click pour copier";
            }
        </script>
    </header>
    <div class="div">
        <h1>Connexion</h1>
        <form method="POST" action="j_security_check">
            <div class="formConectAll" id="Conect-ALL">
                <div class="formConect" id="Conect-Log"><input type="text" name="j_username" placeholder="Login" class="formConect" onmouseover="document.getElementById('Conect-Log').classList.add('select');" onmouseout="document.getElementById('Conect-Log').classList.remove('select');"/></div>
                <div class="formConect" id="Conect-Pwd"><input type="password" name="j_password" placeholder="Mot de passe" class="formConect" onmouseover="document.getElementById('Conect-Pwd').classList.add('select');" onmouseout="document.getElementById('Conect-Pwd').classList.remove('select');"/></div>
                <div class="formConect"><input type="submit" name="connexion" placeholder="Connexion" class="formConectValid" onmouseover="document.getElementById('Conect-ALL').classList.add('select');" onmouseout="document.getElementById('Conect-ALL').classList.remove('select');"/></div>
            </div>
        </form>
    </div>



    </body>
</html>



