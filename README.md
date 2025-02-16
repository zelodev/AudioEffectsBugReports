## The big AudioEffect bugs showcase

New bugs found in the Pixel 9 (AIDL Audio HAL)

- DynamicsProcessing.BandStage can't be disabled once enabled using BandStage.enabled parameter
- DynamicsProcessing max number of bands was restricted to 32. This was only a hard limit when saving and restoring 
  properties (DynamicsProcessing.Settings.getProperties), but now the audio hal is logging a lot of errors when trying
  to use more bands. By enforcing this limit, parametric equalization can no longer be mimicked.
- DynamicsProcessing.mPreferredFrameDuration can no longer be larger than 10ms.
- Virtualizer has no effect at all
- PresetReverb crashes on instantiation
- EnvironmentalReverb crashes on instantiation


Older bugs

- Virtualization can not be used in combination with DynamicsProcessing in Android 14, else all sound will be cut off.
- Virtualizer.forceVirtualizationMode must be called after Android 13 with a delay after enabling Virtualizer, else there is no audible change.
- Bass boost was changed between Android 9 and 10. Since then bass is not boosted but reduced.
- Haptic generator has no effect most of the times, even on supported devices.
- When Haptic generator works, it is interrupted by any other form of haptic feedback and doesn't resume.
  This is especially an issue because none of the listeners below work.
- No callback received from AudioEffect.OnEnableStatusListener
- No callback received from AudioEffect.OnControlStatusChangeListener
- No callback received from OnParameterChangeListener in any of the AudioEffect subclasses