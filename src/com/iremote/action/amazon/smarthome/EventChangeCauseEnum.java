package com.iremote.action.amazon.smarthome;

public enum EventChangeCauseEnum {
    APP_INTERACTION,PHYSICAL_INTERACTION,PERIODIC_POLL,RULE_TRIGGER,VOICE_INTERACTION;
    /**
     *  APP_INTERACTION         	��ʾ���¼����ɿͻ���Ӧ�ó���Ľ�������ġ����磬�ͻ�ʹ��AlexaӦ�ó�����豸��Ӧ���ṩ��Ӧ�ó���򿪵ƻ����š�
     * PHYSICAL_INTERACTION	ָʾ�¼�������˵������������ġ����磬�ֶ��򿪵ƻ��ֶ�����������
     * PERIODIC_POLL                	��ʾ�¼������豸�Ķ�����ѯ����ģ����豸����ֵ�����˱仯�����磬������ÿСʱ��ѯ�¶ȴ��������������µ��¶ȷ��͸�Alexa��
     * RULE_TRIGGER	                ��ʾ���¼�����Ӧ���豸��������ġ����磬����˶���������⵽�˶�����ͻ����ù����Դ򿪵ơ�����������£�Alexa���˶������������¼������ӵƹ������һ���¼�����ָʾ��״̬�������ɹ�������ġ�
     * VOICE_INTERACTION        	��ʾ���¼�������Alexa��������������ġ����磬�û����������ǵ�Echo�豸ͨ����
     */

}
