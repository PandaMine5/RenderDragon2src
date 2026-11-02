$input  a_color0, a_position
$output v_color0


#include "bgfx_shader.h"
#include "func/msaa.h"


uniform vec4 CloudColor;
uniform vec4 FogColor;
uniform vec4 DistanceControl;


void main() {
  vec4 worldPos = mul(u_model[0], a_position);


  const float fadeStartRange = 0.90; // Fade at 90% of visibility
  float fogFade = saturate(1.0 - max(length(worldPos) / DistanceControl.x - fadeStartRange, 0.0));

  
  vec4 cloudTint    = a_color0 * CloudColor;
       cloudTint.a *= fogFade;

  v_color0 = cloudTint;
  gl_Position = applyMSAAJitter(worldPos);
}