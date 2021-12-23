<template>
    <div id="nav">
        <nav class="container navbar navbar-expand-lg navbar-light">
            <img src="@/assets/check-white.png" class="check-logo" />
            <label class="title"> {{title}}</label>
            <div style="flex:1;"></div>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
    
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
    
                <div class="navbar-nav mr-auto">
                    <router-link to="/active" class="nav-item nav-link">Active ToDos</router-link>
                    <router-link to="/completed" class="nav-link">Completed ToDos</router-link>
                    <router-link to="/groups" class="nav-link">Groups</router-link>
                    <div style="flex:1;"></div>
                    <button @click="handleLogout()" class="nav-link logout-button">
                            <span>({{user.email}}) Logout</span>
                        </button>
                </div>
            </div>
    
        </nav>
    </div>
</template>

<script>
export default {
    name: "Nav",
    props: ['title'],
    data() {
        return {
            user:JSON.parse(localStorage.getItem('user')),
        }
    },
    methods: {
        handleLogout() {

            this.$store.dispatch("auth/logout", {}).then(
                (response) => {
                    if (response.success) {
                        this.$router.push("/login");
                    }
                },
                (error) => {
                    console.log(error);
                }
            );
        },
    },
}
</script>

<style>
.check-logo {
    height: 36px;
    margin-top: 16px;
    margin-bottom: 16px;
    margin-left: 16px;
}

#nav {
    background: rgb(82, 113, 133);
    background: -moz-linear-gradient(0deg, rgba(82, 113, 133, 1) 0%, rgba(25, 42, 54, 1) 100%);
    background: -webkit-linear-gradient(0deg, rgba(82, 113, 133, 1) 0%, rgba(25, 42, 54, 1) 100%);
    background: linear-gradient(0deg, rgba(82, 113, 133, 1) 0%, rgba(25, 42, 54, 1) 100%);
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr="#527185", endColorstr="#192a36", GradientType=1);
    padding-bottom: 64px;
}

#nav {
    text-align: center;
}

#nav a {
    font-family: 'Prompt' !important;
    font-weight: 500;
    font-size: 14px;
    color: #98aabd;
}

#nav a.router-link-exact-active {
    color: #eef7fb;
    ;
}

.container {
    padding-top: 0 !important;
    margin: 0 !important;
    margin-left: 4px !important;
    margin-right: 4px !important;
}

.container,
.container-lg,
.container-md,
.container-sm,
.container-xl {
    max-width: calc(100% - 10px) !important;
}

.logout-button {
    background: transparent;
    border: none;
    font-size: 14px;
    color: #98aabd !important;
}

.logout-button:hover {
    color: #eef7fb !important;
}

.title {
    color: #eef7fb;
    font-weight: 600;
    font-size: 16px;
    margin-top: 8px;
    margin-left: 2px;
    display:none;
}

.navbar-toggler-icon {
    background-image: url("~@/assets/menu-bar.png")!important;
}

.navbar-light .navbar-toggler {
    border:none!important; 
}

@media (min-width: 992px) {
    .navbar-expand-lg .navbar-nav {
        flex-direction: row;
        gap: 32px;
        flex: 1;
        
    }
    .navbar {
        padding-bottom:10px!important;
    }
    .title {
        display: none;
    }
}
</style>