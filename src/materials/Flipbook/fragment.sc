$input v_currFrame_uv, v_nextFrame_uv


#include "bgfx_shader.h"


uniform vec4 VBlendControl;
SAMPLER2D_AUTOREG(s_BlitTexture);


void main() {
    vec4 currentFrame = texture2D(s_BlitTexture, v_currFrame_uv);
    vec4 nextFrame    = texture2D(s_BlitTexture, v_nextFrame_uv);
    vec4 frame = currentFrame;


    // Animate block frames
    if (currentFrame.a < 0.01) {
        frame = nextFrame;
    }

    // Animate block frames + blend them
    else if (nextFrame.a >= 0.01) {
        frame = mix(currentFrame, nextFrame, VBlendControl.z);
    }

    gl_FragColor = frame;
}