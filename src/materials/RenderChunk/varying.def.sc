vec3 a_position                : POSITION;
vec4 a_color0                  : COLOR0;
vec2 a_texcoord0               : TEXCOORD0;
vec2 a_texcoord1               : TEXCOORD1;

vec4 v_color0                  : COLOR0;
vec4 v_fog                     : COLOR1;
centroid vec2 v_albedoUV       : TEXCOORD0;
vec2 v_lightmapUV              : TEXCOORD1;
vec2 v_ditheringAndMaskTinting : TEXCOORD2;
vec4 v_clipPosition            : TEXCOORD3;
vec4 v_worldPosition           : TEXCOORD5;