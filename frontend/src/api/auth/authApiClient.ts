import { apiClient } from '@/api/apiClient';
import { IApiResponse } from '@/api/ApiResponse.type';

import { ILogInRequest, ILogInResponse } from '@/api/auth/type/LogIn.type';

export const authApiClient = {

    async logIn(request: ILogInRequest): Promise<string> {
        const response = await apiClient
            .post<IApiResponse<ILogInResponse>>('/auth/login', request);

        return response.data.result.loginToken;
    },
};