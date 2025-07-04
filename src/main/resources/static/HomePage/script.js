document.querySelector(".start-explore").addEventListener("click",(e)=>
{
    Swal.fire({
        title: '📋 Quiz Rules',
        html: `
    ⏱️ <b>Time Limit:</b> 10 minutes<br>
    ✅ <b>All 10 questions must be answered</b> to submit<br>
    🕒 <b>Auto-submit</b> only if all questions are answered before time runs out<br>
    ❌ <b>Incomplete quizzes won’t be submitted</b><br>
    🧠 <b>Each question = 1 mark</b><br>
    📊 <b>Score shown after submission</b><br>
    🔍 <b>See your wrong answers</b> after submitting<br>
    ⚠️ <b>Don't refresh or leave the page</b><br>
  `,
        confirmButtonText: 'OK',
        icon: 'info'
    });

    // alert("clicked");
})
document.addEventListener("DOMContentLoaded", async()=>
{

    //checking if user is login or not
    try{
        await fetch("http://localhost:8080/Check/islogin")
            .then(response=>
              response.json())
            .then((data)=>{
                localStorage.setItem("islogin",data.islogin);
                document.querySelector(".username").innerText=data.username;
                let username=data.username;
                if(data.islogin) {
                    document.querySelector(".login").classList.add("display");
                    document.querySelector(".signup").classList.add("display");
                    document.querySelector(".lock").classList.add("display");
                    document.querySelector(".p-photo").innerText=username.charAt(0).toUpperCase();
                    const logoutForm=document.querySelector(".logout-Handle");

                    let logoutbutton = document.createElement("button");
                    logoutbutton.id="nav-logout";
                    logoutbutton.className="nav-items";
                    logoutbutton.innerText="Logout";
                    logoutForm.appendChild(logoutbutton);

                    document.querySelector(".mobile-login").classList.add("display");
                    document.querySelector(".mobile-signup").classList.add("display");

                    //creating native logout
                    const nativeLogoutDiv=document.createElement("div");
                    nativeLogoutDiv.className="native-logout";
                    nativeLogoutDiv.innerHTML=`<form method="post" action="http://localhost:8080/logout">
                        <button class="native-logoutBtn" type="submit">logout</button>
                        </form>`

                    document.querySelector(".AdditionalContainer").appendChild(nativeLogoutDiv);
                }
                else
                {
                    document.querySelector(".user-profile").classList.add("display");
                    document.querySelector(".notes").classList.add("isdisplay");
                    // logout.classList.add("display");
                }
            }
    )
    }
    catch(error)
    {
        console.log(error.message);
    }



    const cardContainer=document.querySelector(".card-Container");
    // console.log(cardContainer);
    try{
        await fetch("http://localhost:8080/quiz/getCards")
            .then(response=>
            {
                if(!response.ok)
                {
                    throw new Error("Network response was not ok " + response.statusText);
                }
                return response.json();
            })
            .then(data=>
            {

                data.forEach((quiz)=>
                {
                    const card=document.createElement("div");
                    card.className="quiz-card";
                    card.setAttribute("cardid",quiz.cardid);
                    card.innerHTML=`
                    <div class="card" style=" padding: 10px; margin: 10px; background-color: white;>
                        <h2 style="color:black">🧠 ${quiz.quizTitle}</h2>
                       <br/>
                       <br/>
                        <img src="${quiz.imgUrl}" alt="${quiz.quizTitle} quiz" height="100" width="100" >
                        <p>${quiz.quizDescription}</p>
                        <div class="info">
                            <span class="badge">Type : ${quiz.quizTitle}</span>
                            <span class="price">₹${quiz.price}</span>
                        </div>
                        <button class="btn" data-id=${quiz.cardid} data-amount=${quiz.price} value=${quiz.quizTitle}>Buy Now</button>
                        <button class="Sbtn display"value=${quiz.quizTitle} data-cid=${quiz.cardid}>Start Quiz</button>
                        
                    </div>`;
                    cardContainer.appendChild(card);

                    const buyNow=document.querySelectorAll(".btn");
                    buyNow.forEach(button=>
                        button.addEventListener("click",async ()=>
                            {
                                if(localStorage.getItem("islogin")==="false")
                                {
                                    window.location.href="/Authentication/login-signup.html?mode=Login";
                                    return;
                                }
                                const amount=parseInt(button.dataset.amount);
                                const name=button.value;
                                const currency="INR";
                                const cardId=parseInt(button.dataset.id);
                                localStorage.setItem("cardId",cardId);
                                const obj={
                                    amount:amount,
                                    name:name,
                                    currency:currency
                                }
                                // console.log(amount+" "+name+" "+currency);
                                try
                                {
                                    let response=await fetch(`http://localhost:8080/product/v1/checkout`,{
                                        method:"POST",
                                        headers:{
                                            "Content-Type":"application/json"
                                        },
                                        body:JSON.stringify(obj)
                                    });
                                    if(response.ok)
                                    {
                                        response=await response.json();
                                        // console.log(response);
                                        window.location.href=response.sessionUrl;
                                    }
                                    else
                                    {
                                        alert("cannot purchase");

                                    }

                                }
                                catch(error)
                                {
                                    console.log(error.message);
                                }
                            }
                        ))

                }
                )
            })
    }
    catch(error)
    {
        console.log(error.message);
    }
    const button=document.querySelectorAll(".Sbtn");
    button.forEach(button=>
        button.addEventListener("click",()=>
        {
            quiztype=button.value;
            localStorage.setItem("quiztype",quiztype);
            localStorage.setItem("quizid",button.dataset.cid);
            window.location.href=`/QuizQuestions/quiz.html?type=${quiztype}`

        }))

    // console.log(window.location.href);

    const queryString=window.location.search;
    const urlParams=new URLSearchParams(queryString);
    const sessionId = urlParams.get('session_id');

    // console.log(sessionId);

      if(sessionId!==null)
      {

            const cardId=localStorage.getItem("cardId");
            const PaymentStatus=urlParams.get('paymentStatus');
            const Userobj={
                cardId:cardId,
                paymentStatus:PaymentStatus
            };
            const sessionid=sessionId;
            try{
                let response=await fetch(`http://localhost:8080/product/v1/sessionId?sessionId=${sessionId}`,{
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify(Userobj)
                })

                if(response.ok)
                {
                    response=await response.text();
                    Swal.fire({
                        text:data,
                        position: 'top',
                        icon: 'success',
                        confirmButtonText: 'OK'
                    }).then(()=>{window.location.href = "index.html"})
                }
            }
            catch(error)
            {
                console.log(error.message);
            }
      }
      let cards=document.querySelectorAll(".quiz-card");
      cards.forEach(card=>
      {
          let attribute = card.getAttribute("cardid");
          // console.log(attribute);
      })

    if(localStorage.getItem("islogin"))
    {
        try{
            await fetch("http://localhost:8080/quizPurchase/purchase")
                .then(response=>response.json())
                .then(data=> {
                    data.forEach(id=>{
                        cards.forEach(card=>
                        {
                            if(parseInt(id)===parseInt(card.getAttribute("cardid")))
                            {
                                // console.log(id);
                                let elementNodeListOf = card.querySelectorAll(".Sbtn");
                                elementNodeListOf.forEach(ele=>
                                {
                                    ele.classList.remove("display");
                                })
                                let buyNow=card.querySelectorAll(".btn");
                                buyNow.forEach(ele=>
                                {
                                    ele.classList.add("display");
                                })
                            }
                        })
                    })
                })
        }
        catch(error)
        {
            console.log(error.message);
        }
    }
    //notes section
    if(localStorage.getItem("islogin")==="true")
    {
        const sidebarLoader = document.getElementById("sidebar-loader");
        sidebarLoader.style.display = "flex"; // Show loader
        try{
            await fetch("http://localhost:8080/file/getAll")
                .then((response)=>response.json())
                .then((data)=>
                {
                  data.forEach((e)=>{
                      // console.log(e.name)

                      const notes=document.querySelector(".notes-section");

                      const fileList=document.createElement("ul");
                      fileList.className="file-list";

                      fileList.innerHTML=`
                        <li class="file-item" data-fileid="${e.fileId}">${e.name}</li>
                        `
                      notes.appendChild(fileList);
                  });
                })
        }
        catch(error)
        {
            console.log(error.message);
        }
        finally {
            sidebarLoader.style.display = "none"; // Hide loader when done
        }
    }
    //fetching notes
    document.querySelectorAll(".file-item").forEach((item)=>
    {
        item.addEventListener("click",async()=>
        {
            try
            {
                let response= await fetch(`http://localhost:8080/file/get?id=${item.dataset.fileid}`,{
                    method:"POST",
                    headers: {
                        "Content-Type":"application/json"
                    }
                })
                if(response.ok)
                {
                    response=await response.blob();
                    response=URL.createObjectURL(response);//it will convert file into url
                    window.open(response,"_blank");//blank means it will open on new tab
                }
                else
                {
                    console.log("response is not okay");
                }
            }
            catch(error)
            {
                console.log(error.message);
            }
        })
    })

    //top perfomers
    let counter=1;
        try{
            await fetch(`http://localhost:8080/score/max`)
                .then(response=>response.json())
                .then(data=>
                {if (data.length===0)
                {
                    document.querySelector(".top-performer").classList.add("display");
                    return;
                }
                    data.forEach(performer=>
                    {
                        // console.log(performer);
                        const leaderboard= document.createElement("ul");
                        leaderboard.className="leaderboard";

                       leaderboard.innerHTML=`
                     <li class="performer">
                    <div class="rank first">${counter}</div>
                    <div class="performer-info">
                    <div class="name">${performer[0]}</div>
                    <div class="details">based on cumulative score</div>
                    </div>
                    <div class="score">${performer[1]}</div>
                    </li>`

                        document.querySelector(".top-performer").appendChild(leaderboard);
                        counter++;
                    })
                }
                )
        }
        catch(error)
        {
            console.log(error.message)
        }
        // let element = document.querySelector("#nav-logout");


})

const sidebar=document.querySelector(".side-bar");
document.querySelector(".toggle-btn").addEventListener("click",()=>
{
    sidebar.classList.toggle("active");
})