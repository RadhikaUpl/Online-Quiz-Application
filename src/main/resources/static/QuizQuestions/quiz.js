document.addEventListener("DOMContentLoaded",async()=>
{
    let index=1;
    let processedData;
    let storeAnswer=[];
    const quizContainer=document.querySelector(".quiz-wrapper");
    const quizHeading=document.querySelector(".quiz-heading");
    const form=document.querySelector(".quiz-form");
    const score=document.querySelector(".score-para");
    const wrongbtn=document.querySelector(".wrong-btn");
    type=localStorage.getItem("quiztype");

    function escapeHTML(str) {
        return str
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    }

    //timer on left side
    const timerElement = document.getElementById("timer");
    let time = 10 * 60; // 10 minutes in seconds

        const countdown = setInterval(() => {
            const minutes = Math.floor(time / 60);
            const seconds = time % 60;

        timerElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;

        if (time === 0) {
            clearInterval(countdown);
            Swal.fire({
                text:"Time's up! Submitting your quiz...",
                position:'top'
            });
            // Optionally trigger quiz submission
            // document.querySelector("form").submit();

            totalQuestions=index-1;
            let allAnswered=true;

            for(let i=0;i<totalQuestions;i++)
            {
                const selected=document.querySelector(`input[name="q${i+1}"]:checked`);
                if(!selected)
                {
                    allAnswered=false;
                    // alert("your quiz will not submit as you have not selected all the options...")
                    Swal.fire({
                        text:"your quiz will not submit as you have not selected all the options...",
                        position:'top'
                    });
                    window.location.href="/HomePage/index.html";
                    return;
                }
            }
            if(allAnswered){
                form.dispatchEvent(new Event("submit")); //to automatically submit the quiz
            }

        }
        time--;
    }, 1000);
    try{
        await fetch(`http://localhost:8080/quiz/${type}`)
            .then((res)=>
                {
                    if(!res.ok)
                    {
                        throw new Error("Question fetched Wrongly " + response.statusText)
                    }
                    return res.json();
                }
            )
            .then(data=>{
                processedData=data;
                data.forEach((data)=>
                {

                    quizHeading.innerText=`🧠 ${type} Quiz`;
                    const questionDiv=document.createElement("div");
                    questionDiv.className="question";

                    questionDiv.innerHTML=`
                    <h4>${index}.${escapeHTML(data.question) }</h4>
                    <div class="options">
                          <label><input type="radio" name="q${index}" value="${data.optList[0].options}"> ${escapeHTML(data.optList[0].options)}</label>
                          <label><input type="radio" name="q${index}"  value="${data.optList[1].options}">${escapeHTML(data.optList[1].options)} </label>
                          <label><input type="radio" name="q${index}"  value="${data.optList[2].options}"> ${escapeHTML(data.optList[2].options) }</label>
                          <label><input type="radio" name="q${index}"  value="${data.optList[3].options}"> ${escapeHTML(data.optList[3].options) }</label>
                    </div>`
                    index++;
                    form.insertBefore(questionDiv,form.querySelector(".submit-btn"));

                }
                )}
            )


        form.addEventListener("submit", function (e) {
            e.preventDefault(); // prevent default form auto submit

            let totalQuestions = index - 1; // number of questions added
            let allAnswerSelected = true;

            for (let i = 1; i <= totalQuestions; i++) {
                const selected = document.querySelector(`input[name="q${i}"]:checked`);
                if (!selected) {
                    allAnswerSelected = false;
                    // alert(`Please select an option for all Questions`);
                    Swal.fire({
                        text:"Please select an option for all Questions",
                        position:'top'
                    });
                    storeAnswer=[];
                    break; // stop checking further
                }
                else
                {
                    storeAnswer.push({
                        queId:processedData[i-1].questionId,
                        answer:selected.value});
                    localStorage.setItem( processedData[i-1].questionId, selected.value);
                }
            }

            if (allAnswerSelected) {
                // alert("✅ All questions answered! You can now submit.");
                Swal.fire({
                    text:"✅ All questions answered! You can now submit.",
                    position:'top'
                });
                // You can now collect answers and send to backend
                // console.log(storeAnswer);
                sendAnswers(totalQuestions);
                form.classList.add("display");
                score.classList.remove("display");
                wrongbtn.classList.remove("display");
                clearInterval(countdown);
            }
        });

        async function sendAnswers(totalQuestions)
        {

            try{

                let response=await fetch(`http://localhost:8080/validateAns/getScore?quizid=${parseInt(localStorage.getItem("quizid"))}`,{
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify(storeAnswer)
                })
                if(!response.ok)
                {
                    // alert("error in getting score");
                    Swal.fire({
                        text:"error in getting score",
                        position:'top'
                    });
                    return;
                }
                // response=await response.text();
                let data=await response.json();
                // console.log(data);
                score.innerText=`🎉Your Score is : ${data} out of 15`;
                if(totalQuestions===data)
                {
                    wrongbtn.classList.add("display");
                }
            }
           catch(Error)
           {
               console.log(Error.message);
           }
        }
        wrongbtn.addEventListener("click",async()=>
        {
            quizContainer.classList.add("display");
            document.querySelector(".scroll-wrapper").classList.remove("display");
            document.querySelector(".wrongAns-heading").classList.remove("display");
            await getWrongAnswer();
        })
        async function getWrongAnswer()
        {
            let counter=1;
            try{
                let res=await fetch("http://localhost:8080/validateAns/wrongAns")
                if(!res.ok)
                {
                    // alert("error in getting wrong answer");
                    Swal.fire({
                        text:"error in getting wrong answer",
                        position:'top'
                    });
                    return;
                }
               data = await res.json();
                // console.log(data);
                data.forEach((data)=>
                {
                   let que=data.question;
                   let options=data.optList;
                   let correctOption=null;
                    options.forEach((option)=>
                    {
                        if(option.isCorrect)
                        {
                             correctOption=option.options;
                        }
                    })
                    let SelectedAnswer = localStorage.getItem(data.questionId);
                    // console.log(que+" "+" "+correctOption+" "+SelectedAnswer);

                    const queContainer=document.createElement("div");
                    queContainer.className="question-container";

                    queContainer.innerHTML=`
                            <p class="question">Q${counter}.${escapeHTML(que)}</p>
                              <ul class="options">
                                <li class="wrong">Your Answer: ${escapeHTML(SelectedAnswer) }</li>
                                <li class="correct">Correct Answer: ${escapeHTML(correctOption) }</li>
                              </ul>`
                    document.querySelector(".scroll-wrapper").appendChild(queContainer);
                    counter++;
                })
            }
            catch(error)
            {
                console.log(error.message);
            }
        }

    }
    catch(error)
    {
        console.log(error.message);
    }


})