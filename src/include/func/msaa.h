#ifndef MSAA_HEADER_GUARD
#define MSAA_HEADER_GUARD


uniform lowp vec4 SubPixelOffset;


vec4 applyMSAAJitter(vec4 worldPos) {
    mat4 offsetProj = u_proj;

    #if BGFX_SHADER_LANGUAGE_GLSL
        offsetProj[2][0] += SubPixelOffset.x;
        offsetProj[2][1] -= SubPixelOffset.y;
    #else
        offsetProj[0][2] += SubPixelOffset.x;
        offsetProj[1][2] -= SubPixelOffset.y;
    #endif

    return mul(offsetProj, mul(u_view, vec4(worldPos.xyz, 1.0)));
}


#endif //