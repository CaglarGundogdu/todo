<template>
    <transition name="modal-animation">
        <div v-show="modalActive" class="my-modal">
            <transition name="modal-animation-inner">
                <div v-show="modalActive" class="my-modal-inner">
                    <!-- Modal Content-->
                    <slot/>
                    <button @click="close" type="button" class="close-button"> 
                        x
                    </button>
                </div>
            </transition>
        </div>
    </transition>
</template>

<script>
export default {
    name: "Modal",
    props: ["modalActive"],
    setup(props, { emit }) {
        const close = () => {
            emit("close");
        }
        return { close };
    },
}
</script>

<style>
.my-modal {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    width: 100vw;
    position: fixed;
    top: 0;
    left: 0;
    background-color: #303942ab;
    backdrop-filter: blur(5px);
    z-index: 1000;
}

.my-modal-inner {
    position: relative;
    max-width: 640px;
    width: 90%;
    padding: 64px 16px;
    background: #F8FCFF;
    border-radius: 10px;
}

.close-button {
    position: absolute;
    top: 12px;
    right: 12px;
    background: transparent;
    border: none;
    font-weight: 300;
    font-size: 30px;
    font-family: 'Prompt';
    line-height: 28px;
    color: #517185;
}
</style>