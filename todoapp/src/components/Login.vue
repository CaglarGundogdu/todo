<template>
    <div class="login-background">
    
        <div class="login-box">
            <div>
                <img src="@/assets/check.png" height="72" />
                <label class="login-label">Login</label>
    
            </div>
            <div>
                <Form @submit="handleLogin" class="login-form">
                    <div class="form-group">
                        <label for="email" class="login-header">Email</label>
                        <Field name="email" type="email" class="form-input" autocomplete="off" />
                    </div>
                    <div class="form-group">
                        <label for="password" class="login-header">Password</label>
                        <Field name="password" type="password" class="form-input" />
                    </div>
    
                    <div class="form-group">
                        <button class="btn login-button btn-block" :disabled="loading">
                <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
                ></span>
                <span>Login</span>
              </button>
                    </div>
    
                    <div class="form-group">
                        <div v-if="message" class="alert alert-danger" role="alert">
                            {{ message }}
                        </div>
                    </div>
                </Form>
                <label class="register-info-label">Don't have an account yet?</label>
                <button @click="goToRegister()" class="btn register-button btn-block">
                <span>Sign up</span>
              </button>
            </div>
        </div>
    
    </div>
</template>

<script>
import { Form, Field } from "vee-validate";

export default {
    name: "Login",
    components: {
        Form,
        Field,

    },
    data() {

        return {
            loading: false,
            message: "",
        };
    },
    computed: {
        loggedIn() {
            return this.$store.state.auth.status.loggedIn;
        },
    },
    created() {
        if (this.loggedIn) {
            //this.$router.push("/active");
        }
    },
    methods: {
        handleLogin(user) {

            if (user.email == undefined || user.email === "") {
                this.message = "Email can not be empty";
            } else if (user.password == undefined || user.password === "") {
                this.message = "Password can not be empty";
            } else {
                this.loading = true;
                this.$store.dispatch("auth/login", user).then(
                    (response) => {
                        if (response.success) {
                            this.$router.push("/active");
                        } else {
                            this.loading = false;
                            this.message = response.message;
                        }
                    },
                    (error) => {
                        this.loading = false;
                        this.message =
                            (error.response &&
                                error.response.data &&
                                error.response.data.message) ||
                            error.message ||
                            error.toString();
                    }
                );
            }
        },
        goToRegister() {
            this.$router.push('/register');
        }
    },
};
</script>

<style>
.login-background {
    background: rgb(82, 113, 133) !important;
    background: -moz-linear-gradient(0deg, rgba(82, 113, 133, 1) 0%, rgba(25, 42, 54, 1) 100%) !important;
    background: -webkit-linear-gradient(0deg, rgba(82, 113, 133, 1) 0%, rgba(25, 42, 54, 1) 100%) !important;
    background: linear-gradient(0deg, rgba(82, 113, 133, 1) 0%, rgba(25, 42, 54, 1) 100%) !important;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#f3f5f9", endColorstr="#d7dfe7", GradientType=1) !important;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    /*background: radial-gradient(-45deg, #001424, #400286, #21dd6f, #b86910);
	background-size: 400% 400%;
	animation: gradient 15s ease-in-out infinite;*/
}

@keyframes gradient {
    0% {
        background-position: 0% 50%;
    }
    50% {
        background-position: 100% 50%;
    }
    100% {
        background-position: 0% 50%;
    }
}

.login-box {
    background: #FCFEFF;
    width: 400px;
    min-height: 720px;
    margin: 0 auto;
    padding: 32px;
    border-radius: 32px;
    box-shadow: 0px 4px 6px -6px #527286;
    max-height: 800px;
}

.login-header {
    color: #828d99;
    width: 100%;
    text-align: left;
    margin-bottom: -2px;
    font-size: 12px;
    font-family: 'Prompt', sans-serif;
    font-weight: 500;
}

.form-input {
    background: transparent;
    border-bottom: 1px solid #717375;
    border-top: 0px;
    border-left: 0px;
    border-right: 0px;
    border-radius: 0px;
    display: block;
    width: 100%;
    height: calc(1.5em + 0.75rem + 2px);
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: #507083;
    background-clip: padding-box;
    transition: border-color .15s ease-in-out, box-shadow .15s ease-in-out;
}

.form-input:focus {
    color: #507083 !important;
    border-color: #143355;
    outline: 0;
    border-bottom: 2px solid #717375;
}

.login-button {
    color: #fff !important;
    background: rgb(83, 115, 135);
    background: -moz-linear-gradient(136deg, rgba(83, 115, 135, 1) 0%, rgba(23, 39, 50, 1) 100%);
    background: -webkit-linear-gradient(136deg, rgba(83, 115, 135, 1) 0%, rgba(23, 39, 50, 1) 100%);
    background: linear-gradient(137deg, rgba(83, 115, 135, 1) 0%, rgba(23, 39, 50, 1) 100%);
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#537387", endColorstr="#172732", GradientType=1);
    height: 52px;
    font-weight: 500 !important;
    margin-top: 72px;
}

.login-button:hover {
    color: #fff !important;
    background: rgb(60, 84, 99);
    background: -moz-linear-gradient(137deg, rgb(60, 84, 99, 1) 0%, rgb(16, 30, 39, 1) 100%);
    background: -webkit-linear-gradient(137deg, rgb(60, 84, 99, 1) 0%, rgb(16, 30, 39, 1) 100%);
    background: linear-gradient(137deg, rgb(60, 84, 99, 1) 0%, rgb(16, 30, 39, 1) 100%);
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#537387", endColorstr="#172732", GradientType=1);
    height: 52px;
    font-weight: 500 !important;
}

.login-button:focus {
    box-shadow: none !important;
}

.login-label {
    text-align: center;
    margin-top: 24px;
    margin-bottom: 24px;
    color: #143355;
    font-size: 26px;
    width: 100%;
    font-weight: 500;
}

.register-info-label {
    text-align: left;
    margin-top: 32px;
    color: #828d99;
    font-size: 14px;
    width: 100%;
    font-weight: 500;
}

.register-button {
    font-size: 14px !important;
    text-align: left !important;
    margin: 0px !important;
    padding: 0px !important;
    font-weight: 500 !important;
    color: #385161 !important;
}

.register-button:focus {
    box-shadow: none !important;
    font-weight: 600 !important;
}

@media screen and (max-width:400px) {
    .login-box {
        width: 100%;
        height: 100%;
        border-radius: 0;
        box-shadow: none;
    }
}

@media screen and (max-height:400px) {
    .login-box {
        width: 100%;
        height: 100%;
        border-radius: 0;
        box-shadow: none;
        display: flex;
    }
    .login-form {
        width: 360px;
    }
    .login-background {
        width: 100%;
    }
    .login-button {
        margin-top: 0px;
    }
    .register-info-label {
        text-align: left;
        margin-top: 0px;
    }
}
</style>
