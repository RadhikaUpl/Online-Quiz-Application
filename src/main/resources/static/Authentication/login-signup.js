document.addEventListener("DOMContentLoaded",()=>
{
    const loginbtn=document.querySelector(".login");
    const signupbtn=document.querySelector(".signup");
    const name=document.querySelector(".name");
    const cpassword=document.querySelector(".c-password");
    const heading=document.querySelector(".heading");
    const accountLogin=document.querySelector(".account-login");
    const accountSignUp=document.querySelector(".account-signup");
    const mail=document.querySelector(".email");
    const Password=document.querySelector(".password");
    const otpVerify=document.querySelector(".verify-otp");
    const otp=document.querySelector(".otp");


    function loginModel()
    {
        heading.innerHTML="Login to QuizMaster";
        mail.classList.add("display");
        cpassword.classList.add("display");

        loginbtn.classList.remove("alter-style");
        loginbtn.classList.add("style");
        signupbtn.classList.remove("style");
        signupbtn.classList.add("alter-style");

        accountLogin.classList.remove('display');
        accountSignUp.classList.add('display');
    }
    function signupModel()
    {
        heading.innerHTML="Create your account";
        name.classList.remove("display");
        cpassword.classList.remove("display");

        loginbtn.classList.add("alter-style");
        loginbtn.classList.remove("style");
        signupbtn.classList.add("style");
        signupbtn.classList.remove("alter-style");

        accountLogin.classList.add('display');
        accountSignUp.classList.remove('display');
    }

    if(loginbtn && signupbtn)
    {
        loginbtn.addEventListener("click",()=>
        {
            window.location.href="login-signup.html?mode=Login";
            // loginModel();
        })
        signupbtn.addEventListener("click",()=>
        {
            window.location.href="login-signup.html?mode=signUp";
            // signupModel();
        })

        const urlParams=new URLSearchParams(window.location.search);
        const mode=urlParams.get("mode");
        if(mode==="signUp")
        {
            // alert("sign up mode")
            signupModel();
        }
        else
        {
            // alert("login mode");
            loginModel();
        }
    }
    accountSignUp.addEventListener("click",async ()=>
        {
            document.querySelector(".overlay").style.display="flex";
            document.body.style.overflow="hidden";//body will not scroll
            const username=document.querySelector(".name").value;
            const email=document.querySelector(".email").value;
            const password=document.querySelector(".password").value;
            const Cpassword=document.querySelector(".c-password").value;

            if(username==="" || email==="" || password==="" || Cpassword==="")
            {
                document.querySelector(".overlay").style.display="none";
                document.body.style.overflow="auto";
                // alert("Enter complete details");
                Swal.fire({
                    text:"Enter complete details !",
                    position:'top'
                });
                return;
            }

            if(password!==Cpassword)
            {
                document.querySelector(".overlay").style.display="none";
                document.body.style.overflow="auto";

                Swal.fire({
                    text:"Confirm Password is not same as password !",
                    position:'top'
                });
                Password.value="";
                cpassword.value=""
                return;
            }
            const obj={
                username:username,
                email:email,
                password:password
            };

            try{

                const response=await fetch("http://localhost:8080/api/validate",{
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:JSON.stringify(obj)
                });
                document.querySelector(".overlay").style.display="none";
                document.body.style.overflow="auto";
                if(response.ok)
                {
                    // alert("Details are valid , OTP is sent to email")
                    Swal.fire({
                        text:"OTP is sent to Mail !",
                        position:'top'

                    });


                    document.querySelectorAll("input").forEach(input => input.value = "");
                    heading.innerText="Enter OTP";
                    name.classList.add("display");
                    mail.classList.add("display");
                    Password.classList.add("display");
                    cpassword.classList.add("display");
                    accountSignUp.classList.add("display");
                    loginbtn.classList.add("display");
                    signupbtn.classList.add("display");
                    otpVerify.classList.remove("display");
                    otp.classList.remove("display");

                    otpVerify.addEventListener("click",async()=>{
                        // alert("clicked");
                        const otpCode=document.querySelector(".otp").value;
                        if(otpCode==="")
                        {
                            // alert("Enter the otp");
                            Swal.fire({
                                text:"Enter the OTP !",
                                position:'top'
                            });
                        }
                        else
                        {
                            try{
                                const response=await fetch(`http://localhost:8080/api/addNewUser?otp=${otpCode}`,{
                                    method:"POST",
                                    headers:{
                                        "Content-Type":"application/json"
                                    },
                                    body:JSON.stringify(obj)
                                });
                                if(!response.ok)
                                {
                                    Swal.fire({
                                        text:"Invalid OTP !",
                                        position:'top'

                                    });
                                    return;

                                }
                                // alert("successfully signed Up");
                                Swal.fire({
                                    title: "Successfully Signed Up !",
                                    icon: "success",
                                    draggable: true
                                    })
                            .then(() => {
                                    window.location.href = "/Authentication/login-signup.html?mode=Login";
                                });
                            }
                            catch(error)
                            {
                                // alert(error.message);
                                Swal.fire({
                                    text:error.message,
                                    position:'top'

                                });
                            }
                        }

                    })
                }
                else
                {
                    const errorData = await response.text();
                    // alert("Signup failed: " + errorData);
                    Swal.fire({
                        text:"Signup failed: "+errorData,
                        position:'top'
                    });

                }
            }
            catch (error) {
                // alert("Error connecting to server: " + error.message);
                Swal.fire({
                    text:"Error connecting to server: "+error.message,
                    position:'top'

                });
            }
        }
    )



    accountLogin.addEventListener("click",()=>
    {
        // alert("clicked");
        const username=document.querySelector(".name").value;
         const password=document.querySelector(".password").value;
         // console.log(username+","+password);


    })
})

