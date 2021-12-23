<template>
  <div class="login-background">
  
  <div class="login-box">
    <div>
      <img src="@/assets/check.png" height="72"/>
    <label class="login-label" >Sign up</label>
    
    </div>
    <div>
      <Form @submit="handleRegister" class="login-form">
        <div class="form-group">
          <label for="email" class="login-header" >Email</label>
          <Field name="email" type="email" class="form-input" autocomplete="off" />
        </div>
        <div class="form-group">
          <label for="password" class="login-header">Password</label>
          <Field name="password" type="password" class="form-input" />
        </div>
        <div class="form-group">
          <label for="passwordConfirmation" class="login-header">Password Confirmation</label>
          <Field name="passwordConfirmation" type="password" class="form-input" />
        </div>
        <div class="form-group">
          <button class="btn login-button btn-block" :disabled="loading">
            <span
              v-show="loading"
              class="spinner-border spinner-border-sm"
            ></span>
            <span>Sign up</span>
          </button>
        </div>

        <div class="form-group">
          <div v-if="message" class="alert alert-danger" role="alert">
            {{ message }}
          </div>
        </div>
      </Form>
      <label class="register-info-label" >Do have an account?</label>
      <button @click="goToLogin()" class="btn register-button btn-block" >
            <span>Login</span>
          </button>
          </div>
      </div>

  </div>
  
</template>

<script>
import { Form, Field } from "vee-validate";

export default {
  name: "Register",
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
    handleRegister(user) {
      if (user.email == undefined || user.email === "") {
        this.message = "Email can not be empty";
      }
      else if (user.password == undefined || user.password === "") {
        this.message = "Password can not be empty";
      }
      else if (user.password != user.passwordConfirmation) {
        this.message = "Passwords do NOT match";
      } else { 
        this.loading = true;
        this.$store.dispatch("auth/register", user).then(
          (response) => {
            if (response.success) {
            this.$router.push("/login");
            } else{
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
          });
      }
      
    },
    goToLogin() {        
          this.$router.push('/login'); 
    }
  },
};
</script>

<style>

</style>
